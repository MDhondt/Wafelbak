package be.scoutsronse.wafelbak.view.component.treeview;

import be.scoutsronse.wafelbak.domain.dto.ClusterDto;
import be.scoutsronse.wafelbak.domain.id.ClusterId;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.util.Callback;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.lang.Integer.toHexString;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static javafx.scene.input.TransferMode.MOVE;

public class DragDropCellFactory implements Callback<TreeView<ClusterItem>, TreeCell<ClusterItem>> {

    private static final DataFormat JAVA_FORMAT = new DataFormat("application/x-java-serialized-object");

    private SearchableClusterTreeView source;
    private Collection<Triple<SearchableClusterTreeView, Predicate<ClusterDto>, Consumer<ClusterDto>>> allowedSources;
    private Collection<String> allowedSourceIds;
    private Consumer<ClusterId> doubleClickConsumer;

    public DragDropCellFactory(SearchableClusterTreeView source, Collection<Triple<SearchableClusterTreeView, Predicate<ClusterDto>, Consumer<ClusterDto>>> allowedSources, Consumer<ClusterId> doubleClickConsumer) {
        this.source = source;
        this.allowedSources = allowedSources;
        this.allowedSourceIds = allowedSources.stream().map(Triple::getLeft).map(view -> view.treeView).map(TreeView::hashCode).map(Integer::toHexString).collect(toSet());
        this.doubleClickConsumer = doubleClickConsumer;
    }

    @Override
    public TreeCell<ClusterItem> call(TreeView<ClusterItem> param) {
        TreeCell<ClusterItem> cell = new TreeCell<ClusterItem>() {
            @Override
            protected void updateItem(ClusterItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.getName());
                }
            }
        };

        cell.setOnDragDetected(event -> {
            ClusterItem draggedItem = cell.getItem();
            if (!(draggedItem instanceof ClusterData))
                return;
            ClusterDto draggedCluster = source.visibleClusters.stream().filter(dto -> dto.id.equals(((ClusterData) draggedItem).getId())).findFirst().get();
            Dragboard db = cell.startDragAndDrop(MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(JAVA_FORMAT, draggedCluster);
            content.putString(toHexString(param.hashCode()));
            db.setContent(content);
            db.setDragView(cell.snapshot(null, null));
            event.consume();
        });

        cell.setOnDragOver(event -> {
            if (event.getDragboard().hasContent(JAVA_FORMAT) && event.getGestureSource() != null && allowedSourceIds.contains(event.getDragboard().getString())) {
                event.acceptTransferModes(MOVE);
            }
            event.consume();
        });

        cell.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (event.getDragboard().hasContent(JAVA_FORMAT) && event.getGestureSource() != null && allowedSourceIds.contains(event.getDragboard().getString())) {
                ClusterDto draggedCluster = (ClusterDto) db.getContent(JAVA_FORMAT);
                Triple<SearchableClusterTreeView, Predicate<ClusterDto>, Consumer<ClusterDto>> allowedSource =
                        allowedSources.stream().filter(triple -> event.getDragboard().getString().equals(toHexString(triple.getLeft().treeView.hashCode()))).findFirst().get();

                if (allowedSource.getMiddle().test(draggedCluster)) {
                    SearchableClusterTreeView dragSource = allowedSource.getLeft();
                    dragSource.setContent(dragSource.allClusters.stream().filter(cluster -> !cluster.id.equals(draggedCluster.id)).collect(toSet()));
                    dragSource.search(dragSource.searchBox.getText());

                    source.setContent(concat(of(draggedCluster), source.allClusters.stream()).collect(toSet()));
                    source.search(source.searchBox.getText());
                    TreeItem<ClusterItem> newItem = source.treeView.getRoot().getChildren().stream().filter(item -> ((ClusterData) item.getValue()).getId().equals(draggedCluster.id)).findFirst().get();
                    source.treeView.getSelectionModel().select(newItem);
                    source.treeView.getSelectionModel().select(newItem);

                    allowedSource.getRight().accept(draggedCluster);

                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        cell.setOnMouseClicked(event -> {
            if (doubleClickConsumer != null && event.getClickCount() == 2 && !cell.isEmpty()) {
                ClusterItem item = cell.getItem();
                if (!(item instanceof ClusterData))
                    return;
                ClusterId clusterId = ((ClusterData) item).getId();
                doubleClickConsumer.accept(clusterId);
            }
        });

        return cell;
    }
}
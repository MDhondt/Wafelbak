package be.scoutsronse.wafelbak.domain.id;

public class ClusterStateId extends Id {

    private ClusterStateId(Long id) {
        super(id);
    }

    public static ClusterStateId aClusterStateId(Long id) {
        return new ClusterStateId(id);
    }
}
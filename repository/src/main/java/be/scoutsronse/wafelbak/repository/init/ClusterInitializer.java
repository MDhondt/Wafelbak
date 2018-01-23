package be.scoutsronse.wafelbak.repository.init;

import be.scoutsronse.wafelbak.domain.entity.Cluster;
import be.scoutsronse.wafelbak.domain.entity.Street;
import be.scoutsronse.wafelbak.repository.ClusterRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static be.scoutsronse.wafelbak.domain.id.WayId.aWayId;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Component
public class ClusterInitializer {

    @Inject
    private ClusterRepository clusterRepository;

    @PostConstruct
    public void init() {
        if (clusterRepository.findAll().isEmpty()) {
            clusterRepository.saveAll(asList(
                    groteMarkt(),
                    klijpeStraat(),
                    mussenStraat(),
                    floreal(),
                    kapellestraat(),
                    zonnestraat()
            ));
        }
    }

    private Cluster groteMarkt() {
        Street markt = new Street("Grote Markt", asList(aWayId(8117104L), aWayId(8117543L), aWayId(353906474L)));
        return new Cluster("Grote Markt", singletonList(markt));
    }

    private Cluster klijpeStraat() {
        Street paillartcamp = new Street("Paillartcamp", asList(aWayId(108206656L), aWayId(329550281L), aWayId(329550284L), aWayId(413684323L)));
        Street coolstraat = new Street("Coolstraat", singletonList(aWayId(109420699L)));
        Street klijpestraat = new Street("Klijpestraat", asList(aWayId(33926171L), aWayId(229044692L), aWayId(329550291L), aWayId(329550293L)));
        Street rozenaaksesteenweg = new Street("Rozenaaksesteenweg", asList(aWayId(5117167L), aWayId(86502124L), aWayId(121761984L), aWayId(229044693L)));
        Street zonnestraat = new Street("Zonnestraat", asList(aWayId(33998781L), aWayId(302625323L), aWayId(313602925L)));
        Street rodemutslaan = new Street("Rode Mutslaan", asList(aWayId(9385408L), aWayId(329550297L), aWayId(329550299L)));
        Street bakkereel = new Street("Bakkereel", asList(aWayId(113575674L), aWayId(113575675L), aWayId(113575679L)));
        Street doorniksesteenweg = new Street("Doorniksesteenweg", asList(aWayId(113868171L), aWayId(313776848L), aWayId(313776852L), aWayId(313776854L)));
        Street doornik = new Street("Oude Doorniksesteenweg", singletonList(aWayId(41265933L)));
        return new Cluster("Klijpestraat", asList(paillartcamp, coolstraat, doornik, klijpestraat, rozenaaksesteenweg, zonnestraat, rodemutslaan, bakkereel, doorniksesteenweg));
    }

    private Cluster mussenStraat() {
        Street mussenstraat = new Street("Mussenstraat", asList(aWayId(121761976L), aWayId(121771035L), aWayId(403116851L)));
        Street steenveld = new Street("Steenveldstraat", singletonList(aWayId(108448565L)));
        Street blokstraat = new Street("Blokstraat", singletonList(aWayId(86502119L)));
        Street molekens = new Street("Molekensstraat", singletonList(aWayId(108211844L)));
        Street bierink = new Street("Bierinkstraat", asList(aWayId(108448566L), aWayId(121761968L)));
        Street ossestraat = new Street("Ossestraat", asList(aWayId(60688059L), aWayId(121771036L), aWayId(174916918L)));
        Street scherpenberg = new Street("Scherpenberg", asList(aWayId(75542893L), aWayId(114457689L), aWayId(174916977L), aWayId(374730266L)));
        Street riekestraat = new Street("Riekestraat", asList(aWayId(60688050L), aWayId(121761983L), aWayId(141860160L)));
        Street hotondstraat = new Street("Hotondstraat", singletonList(aWayId(114457690L)));
        return new Cluster("Mussenstraat", asList(mussenstraat, steenveld, blokstraat, molekens, bierink, ossestraat, scherpenberg, riekestraat, hotondstraat));
    }

    private Cluster floreal() {
        Street floreal = new Street("Floréal", asList(aWayId(113575661L), aWayId(113575664L), aWayId(113575665L), aWayId(113575666L), aWayId(113575667L), aWayId(113575676L), aWayId(113575680L), aWayId(113575682L), aWayId(113575684L), aWayId(113575685L), aWayId(113575687L), aWayId(184426711L), aWayId(184426712L)));
        Street doornik = new Street("Oude Doorniksesteenweg", singletonList(aWayId(113575668L)));
        Street george = new Street("Georges de Myttenaerestraat", singletonList(aWayId(174916778L)));
        Street engelsenlaan = new Street("Engelsenlaan", asList(aWayId(34016012L), aWayId(138304378L)));
        return new Cluster("Floréal", asList(floreal, doornik, george, engelsenlaan));
    }

    private Cluster kapellestraat() {
        Street zonnestraat = new Street("Zonnestraat", asList(aWayId(138304376L), aWayId(138304379L), aWayId(302625324L), aWayId(313643561L), aWayId(112123941L), aWayId(226140013L), aWayId(319960399L), aWayId(319960400L)));
        Street kapellestraat = new Street("Kapellestraat", asList(aWayId(25711651L), aWayId(121771030L), aWayId(141860159L), aWayId(319960342L), aWayId(319960343L)));
        Street hof = new Street("Hof Geenensstraat", singletonList(aWayId(133398485L)));
        Street eikel = new Street("Eikelstraat", singletonList(aWayId(133398453L)));
        Street noord = new Street("Noordstraat", singletonList(aWayId(114968171L)));
        Street fiertelmeers = new Street("Fiertelmeers", asList(aWayId(37130456L), aWayId(86502142L), aWayId(377184509L)));
        Street schaffendal = new Street("Schaffendal", singletonList(aWayId(377182029L)));
        return new Cluster("Kapellestraat", asList(zonnestraat, kapellestraat, hof, eikel, noord, fiertelmeers, schaffendal));
    }

    private Cluster zonnestraat() {
        Street zonnestraat = new Street("Zonnestraat", asList(aWayId(8117386L), aWayId(226011568L), aWayId(226011570L), aWayId(226011571L), aWayId(226011573L), aWayId(319960397L), aWayId(319960398L), aWayId(319960401L), aWayId(108210621L), aWayId(121772634L), aWayId(141861008L), aWayId(226011562L)));
        Street spinster = new Street("Spinstersstraat", asList(aWayId(37130141L), aWayId(141861006L), aWayId(226011559L)));
        Street gasmeter = new Street("Gasmeterstraat", singletonList(aWayId(108211842L)));
        Street voorzienigheidsstraat = new Street("Voorzienigheidsstraat", singletonList(aWayId(218678805L)));
        Street bereidersstraat = new Street("Bereidersstraat", singletonList(aWayId(169173365L)));
        Street elfnovember = new Street("Elfnovemberstraat", singletonList(aWayId(108211842L)));
        Street emanuel = new Street("Emanuel Hielstraat", singletonList(aWayId(108211845L)));
        Street victor = new Street("Victor Lagachestraat", singletonList(aWayId(108211841L)));
        Street langehaag = new Street("Langehaag", asList(aWayId(37130143L), aWayId(393001608L)));
        Street opgeeisten = new Street("Opgeëistenstraat", asList(aWayId(75542894L), aWayId(166248332L)));
        Street aime = new Street("Aime Delhayeplein", asList(aWayId(108210614L), aWayId(353437360L)));
        return new Cluster("Zonnestraat", asList(zonnestraat, spinster, gasmeter, victor, elfnovember, emanuel, voorzienigheidsstraat, bereidersstraat, langehaag, opgeeisten, aime));
    }
}
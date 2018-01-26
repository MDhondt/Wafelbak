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
                    zonnestraat(),
                    engelsenlaan(),
                    ambachtelijkeZone(),
                    viermaartlaan(),
                    stationstraat(),
                    vrijheid(),
                    kruisstraat(),
                    kleve(),
                    hogerlucht(),
                    bruul()
            ));
        }
    }

    private Cluster groteMarkt() {
        Street markt = new Street("Grote Markt", asList(aWayId(8117104L), aWayId(8117543L), aWayId(353906474L)));
        Street peperstraat = new Street("Peperstraat", singletonList(aWayId(8117100L)));
        return new Cluster("Grote Markt", asList(markt, peperstraat));
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

    private Cluster engelsenlaan() {
        Street engelsenlaan = new Street("Engelsenlaan", singletonList(aWayId(8117121L)));
        Street oswald = new Street("Oswald Ponettestraat", asList(aWayId(8117719L), aWayId(33995626L)));
        Street edmond = new Street("Edmond Puissantstraat", singletonList(aWayId(75392197L)));
        Street demerode = new Street("De Mérodestraat", singletonList(aWayId(113578056L)));
        Street jan = new Street("Jan van Nassaustraat", asList(aWayId(8117701L), aWayId(348724032L)));
        Street cornelis = new Street("Sint-Cornelisstraat", asList(aWayId(33995625L), aWayId(393241448L)));
        Street ives = new Street("Ives- Sabin Maghermanlaan", asList(aWayId(75392200L), aWayId(349638423L)));
        Street alex = new Street("Alexandre-Louis Vanhovestraat", singletonList(aWayId(8117164L)));
        Street park = new Street("Parkstraat", singletonList(aWayId(11838060L)));
        Street neerhof = new Street("Neerhofstraat", singletonList(aWayId(11838035L)));
        return new Cluster("Engelsenlaan", asList(engelsenlaan, oswald, edmond, demerode, jan, cornelis, alex, park, neerhof, ives));
    }

    private Cluster ambachtelijkeZone() {
        Street pont = new Street("Pontstraat", asList(aWayId(121772367L), aWayId(121772368L), aWayId(121772369L), aWayId(121772370L), aWayId(395442929L)));
        Street malaise = new Street("Malaise", singletonList(aWayId(33926172L)));
        Street biest = new Street("Biest", asList(aWayId(33926179L), aWayId(37129700L)));
        Street oude = new Street("Oude Leuzesesteenweg", singletonList(aWayId(37129703L)));
        Street leuzesesteenweg = new Street("Leuzesesteenweg", asList(aWayId(8117105L), aWayId(8590081L), aWayId(33967709L), aWayId(121772360L), aWayId(121772361L), aWayId(363358558L)));
        return new Cluster("Ambachtelijke Zone", asList(pont, malaise, biest, oude, leuzesesteenweg));
    }

    private Cluster viermaartlaan() {
        Street viermaartlaan = new Street("Viermaartlaan", asList(aWayId(8117009L), aWayId(413577540L), aWayId(444559451L)));
        Street gomar = new Street("Gomar Vandewielelaan", asList(aWayId(8117160L), aWayId(226011545L), aWayId(226139982L)));
        Street royers = new Street("Gustave Royerslaan", asList(aWayId(8117152L), aWayId(10901060L)));
        Street albert = new Street("Albert Vandeputtestraat", singletonList(aWayId(10525881L)));
        Street leopold = new Street("Leopold Sturbautstraat", singletonList(aWayId(8117173L)));
        Street louis = new Street("Louis Vangrootenbruelstraat", singletonList(aWayId(10525811L)));
        Street frederic = new Street("Frédéric Bruneellaan", singletonList(aWayId(28208474L)));
        return new Cluster("Royerslaan", asList(viermaartlaan, gomar, royers, albert, leopold, louis, frederic));
    }

    private Cluster stationstraat() {
        Street charles = new Street("Charles de Gaullestraat", singletonList(aWayId(8590082L)));
        Street pierre = new Street("Pierre D'Hauwerstraat", singletonList(aWayId(8117176L)));
        Street couplevoie = new Street("Coupl' voie", singletonList(aWayId(8117509L)));
        Street fostierlaan = new Street("Fostierlaan", singletonList(aWayId(8590085L)));
        Street hoogstraat = new Street("Hoogstraat", singletonList(aWayId(23768101L)));
        Street collegestraat = new Street("Collegestraat", singletonList(aWayId(28695957L)));
        Street stationstraat = new Street("Stationstraat", singletonList(aWayId(8117065L)));
        Street gefusilleerdenlaan = new Street("Gefusilleerdenlaan", asList(aWayId(8117174L), aWayId(226011542L)));
        Street abeelstraat = new Street("Abeelstraat", asList(aWayId(8117614L), aWayId(389539746L)));
        Street oudstrijderslaan = new Street("Oudstrijderslaan", asList(aWayId(8117564L), aWayId(225977078L), aWayId(225977079L), aWayId(313345711L)));
        return new Cluster("Stationstraat", asList(charles, pierre, collegestraat, fostierlaan, hoogstraat, couplevoie, stationstraat, gefusilleerdenlaan, abeelstraat, oudstrijderslaan));
    }

    private Cluster vrijheid() {
        Street kasteelstraat = new Street("Kasteelstraat", singletonList(aWayId(10525634L)));
        Street schipstraat = new Street("Schipstraat", singletonList(aWayId(8117663L)));
        Street cypriaan = new Street("Cypriaan de Rosestraat", singletonList(aWayId(350948973L)));
        Street watermolenstraat = new Street("Watermolenstraat", singletonList(aWayId(10526144L)));
        Street kegelkaai = new Street("Kegelkaai", singletonList(aWayId(138304377L)));
        Street hermes = new Street("Sint-Hermesstraat", singletonList(aWayId(8117688L)));
        Street priestersstraat = new Street("Priestersstraat", singletonList(aWayId(435682723L)));
        Street fabriekstraat = new Street("Fabriekstraat", asList(aWayId(42834321L), aWayId(135517455L)));
        Street rooseveltplein = new Street("Franklin Rooseveltplein", asList(aWayId(8117071L), aWayId(141861009L)));
        Street wijnstraat = new Street("Wijnstraat", asList(aWayId(8117697L), aWayId(437726571L)));
        Street kaatsspelplein = new Street("Kaatsspelplein", asList(aWayId(10526152L), aWayId(435684680L)));
        Street sintmartensstraat = new Street("Sint-Martensstraat", asList(aWayId(8117467L), aWayId(8117661L), aWayId(34207569L), aWayId(435682724L), aWayId(435766874L), aWayId(435766875L)));
        return new Cluster("Vrijheid", asList(kasteelstraat, schipstraat, cypriaan, watermolenstraat, kegelkaai, hermes, priestersstraat, fabriekstraat, rooseveltplein, wijnstraat, kaatsspelplein, sintmartensstraat));
    }

    private Cluster kruisstraat() {
        Street kruisstraat = new Street("Kruisstraat", asList(aWayId(28123748L), aWayId(86502129L), aWayId(86502134L), aWayId(138223727L), aWayId(226011547L), aWayId(226011548L), aWayId(228830273L), aWayId(302625325L), aWayId(313842906L), aWayId(313842907L), aWayId(313842908L), aWayId(377178941L), aWayId(377178942L), aWayId(411451083L), aWayId(411451084L)));
        Street schavaart = new Street("Schavaart", asList(aWayId(37130459L), aWayId(138223903L), aWayId(377179101L)));
        Street schaffendal = new Street("Schaffendal", asList(aWayId(133656837L), aWayId(377181024L)));
        Street oudestraat = new Street("Oudestraat", asList(aWayId(11838447L), aWayId(363353619L)));
        Street vlamingenweg = new Street("Vlamingenweg", asList(aWayId(133398418L), aWayId(138223920L)));
        Street cyriel = new Street("Cyriel Buyssestraat", singletonList(aWayId(37130458L)));
        Street groeneweg = new Street("Groeneweg", singletonList(aWayId(37130457L)));
        Street hulstken = new Street("Hulstken", singletonList(aWayId(37130519L)));
        Street remi = new Street("Remi Van Caeneghemstraat", singletonList(aWayId(133398477L)));
        return new Cluster("Kruisstraat", asList(kruisstraat, schavaart, schaffendal, oudestraat, vlamingenweg, cyriel, groeneweg, hulstken, remi));
    }

    private Cluster kleve() {
        Street broeke = new Street("Broeke", asList(aWayId(14808832L), aWayId(37130646L), aWayId(37130651L), aWayId(121772387L), aWayId(313880314L), aWayId(444173445L)));
        Street klevestraat = new Street("Klevestraat", asList(aWayId(166248464L), aWayId(166248465L), aWayId(166248466L), aWayId(166249189L), aWayId(166249190L), aWayId(404121380L), aWayId(404121381L), aWayId(404121382L)));
        Street broekestraat = new Street("Broekestraat", asList(aWayId(8117491L), aWayId(11838300L), aWayId(228830272L), aWayId(313880313L)));
        Street jacob = new Street("Jacob Van Arteveldestraat", asList(aWayId(166790567L), aWayId(166790601L)));
        Street abel = new Street("Abel Regibostraat", singletonList(aWayId(37130647L)));
        Street edouard = new Street("Edouard Jolystraat", singletonList(aWayId(37130648L)));
        Street jablonecstraat = new Street("Jablonecstraat", singletonList(aWayId(37130649L)));
        Street sandwichstraat = new Street("Sandwichstraat", singletonList(aWayId(166790598L)));
        Street hemelberg = new Street("Hemelberg", singletonList(aWayId(135517452L)));
        Street ernestine = new Street("Ernestine de Lignestraat", singletonList(aWayId(355040390L)));
        return new Cluster("Kleve", asList(broeke, klevestraat, broekestraat, jacob, abel, edouard, jablonecstraat, sandwichstraat, hemelberg, ernestine));
    }

    private Cluster hogerlucht() {
        Street glorieuxlaan = new Street("Stefaan-Modest Glorieuxlaan", asList(aWayId(11845803L), aWayId(86502122L), aWayId(86502125L), aWayId(113872516L), aWayId(113872517L), aWayId(113872518L), aWayId(113872522L), aWayId(113872529L), aWayId(113872531L), aWayId(135517453L), aWayId(135517458L), aWayId(226011560L), aWayId(226139998L), aWayId(226139999L), aWayId(226140000L), aWayId(437538489L), aWayId(437538491L)));
        Street hogerlucht = new Street("Hogerlucht", asList(aWayId(86502135L), aWayId(121772353L), aWayId(390856820L)));
        Street kammeland = new Street("Kammeland", asList(aWayId(14808453L), aWayId(226011546L)));
        Street wolvestraat = new Street("Wolvestraat", asList(aWayId(11838640L), aWayId(135517454L)));
        Street jean = new Street("Dokter Jean Cuvelierstraat", asList(aWayId(37130902L), aWayId(226011537L)));
        Street sintambrosius = new Street("Sint-Ambrosiusstraat", singletonList(aWayId(11838480L)));
        return new Cluster("Hogerlucht", asList(glorieuxlaan, hogerlucht, kammeland, wolvestraat, jean, sintambrosius));
    }

    private Cluster bruul() {
        Street spillegem = new Street("Spillegem", singletonList(aWayId(8117544L)));
        Street veemarkt = new Street("Veemarkt", singletonList(aWayId(8117644L)));
        Street priestersstraat = new Street("Priestersstraat", singletonList(aWayId(8117545L)));
        Street verlorenstraat = new Street("Verlorenstraat", singletonList(aWayId(8590343L)));
        Street jean = new Street("Jean-Baptiste Mouroitplein", singletonList(aWayId(121772397L)));
        Street biesestraat = new Street("De Biesestraat", asList(aWayId(8117542L), aWayId(313880318L)));
        Street sintpieter = new Street("Sint-Pietersnieuwstraat", asList(aWayId(8117547L), aWayId(8117686L)));
        Street bruul = new Street("Bruul", asList(aWayId(11845810L), aWayId(135517451L), aWayId(313880317L)));
        return new Cluster("Bruul", asList(spillegem, veemarkt, priestersstraat, jean, biesestraat, sintpieter, bruul, verlorenstraat));
    }
}
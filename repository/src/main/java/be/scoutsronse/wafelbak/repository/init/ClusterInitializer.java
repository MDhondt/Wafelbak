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
                    bruul(),
                    zuidstraat(),
                    walenweg(),
                    bordetlaan(),
                    stookt(),
                    langeweg(),
                    aatstraat(),
                    scheldekouter(),
                    elzelestraat(),
                    elzeelsesteenweg(),
                    rotterij(),
                    maquisstraat(),
                    germinal(),
                    maagdenstraat(),
                    ninovestraat(),
                    savooistraat(),
                    dammekensstraat(),
                    ninoofsesteenweg()
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

    private Cluster zuidstraat() {
        Street joseph = new Street("Joseph Ferrantstraat", singletonList(aWayId(8117588L)));
        Street politiekegevangenenstraat = new Street("Politiekegevangenenstraat", singletonList(aWayId(28695997L)));
        Street jeanBaptiste = new Street("Jean-Baptiste Dekeyserstraat", singletonList(aWayId(8117179L)));
        Street charles = new Street("Charles Vandendoorenstraat", singletonList(aWayId(8117193L)));
        Street kerkplein = new Street("Kerkplein", asList(aWayId(8117610L), aWayId(319960345L)));
        Street oudeVesten = new Street("Oude Vesten", asList(aWayId(184406098L), aWayId(313880323L)));
        Street zuidstraat = new Street("Zuidstraat", asList(aWayId(8117181L), aWayId(8117189L), aWayId(319960403L)));
        Street ijzerstraat = new Street("IJzerstraat", asList(aWayId(28695974L), aWayId(221233976L), aWayId(313345710L), aWayId(319960337L)));
        return new Cluster("Zuidstraat", asList(joseph, politiekegevangenenstraat, jeanBaptiste, charles, kerkplein, oudeVesten, zuidstraat, ijzerstraat));
    }

    private Cluster walenweg() {
        Street walenweg = new Street("Walenweg", singletonList(aWayId(34206416L)));
        Street hersenplank = new Street("Hersenplank", singletonList(aWayId(37129698L)));
        Street camille = new Street("Camille Lemonnierlaan", singletonList(aWayId(33268544L)));
        Street constantin = new Street("Constantin Meunierstraat", singletonList(aWayId(34206490L)));
        Street modeste = new Street("Modeste de Noyettestraat", singletonList(aWayId(34206415L)));
        Street fernand = new Street("Fernand Duboisstraat", asList(aWayId(34206593L), aWayId(34206594L)));
        Street victor = new Street("Victor Hortastraat", asList(aWayId(34206591L), aWayId(34206592L)));
        Street saint = new Street("Saint-Sauveurstraat", asList(aWayId(34206414L), aWayId(121772374L), aWayId(137136753L), aWayId(395265577L)));
        return new Cluster("Walenweg", asList(walenweg, hersenplank, camille, constantin, modeste, fernand, victor, saint));
    }

    private Cluster bordetlaan() {
        Street aatstraat = new Street("Aatstraat", singletonList(aWayId(28695985L)));
        Street jules = new Street("Jules Bordetlaan", singletonList(aWayId(8590411L)));
        Street david = new Street("David Teniersstraat", singletonList(aWayId(37131860L)));
        Street louis = new Street("Louis Pasteurlaan", singletonList(aWayId(148455817L)));
        Street molendam = new Street("Molendam", asList(aWayId(25772244L), aWayId(221233966L)));
        Street rodeBroeckstraat = new Street("Rode Broeckstraat", asList(aWayId(37070972L), aWayId(226011554L)));
        Street armand = new Street("Armand Demeulemeesterstraat", asList(aWayId(175193389L), aWayId(350894887L)));
        Street auguste = new Street("Auguste Payenstraat", asList(aWayId(350878611L), aWayId(350881823L), aWayId(350882017L)));
        return new Cluster("Jules Bordetlaan", asList(aatstraat, jules, david, louis, molendam, rodeBroeckstraat, armand, auguste));
    }

    private Cluster stookt() {
        Street stookt = new Street("Stookt", singletonList(aWayId(34347706L)));
        Street vanEyckstraat = new Street("Gebroeders Van Eyckstraat", singletonList(aWayId(36772713L)));
        Street georges = new Street("Georges Dumontstraat", singletonList(aWayId(37067289L)));
        Street square = new Street("Square Georges Eeckhout", singletonList(aWayId(37040675L)));
        Street edmond = new Street("Edmond Picardlaan", singletonList(aWayId(37067287L)));
        Street eeckoutstraat = new Street("Georges Eeckoutstraat", asList(aWayId(34347117L), aWayId(121772373L)));
        Street joseph = new Street("Joseph Wautersplein", asList(aWayId(37073474L), aWayId(37073476L)));
        Street hector = new Street("Hector Denisstraat", asList(aWayId(37067288L), aWayId(129776637L), aWayId(129776638L)));
        Street ernest = new Street("Ernest Solvaystraat", asList(aWayId(37040674L), aWayId(129776635L), aWayId(129776636L), aWayId(226011541L)));
        return new Cluster("Stookt", asList(stookt, vanEyckstraat, georges, square, edmond, eeckoutstraat, joseph, hector, ernest));
    }

    private Cluster langeweg() {
        Street isidoor = new Street("Isidoor Opsomerstraat", singletonList(aWayId(37073478L)));
        Street achturenstraat = new Street("Achturenstraat", singletonList(aWayId(37073477L)));
        Street zonnebloemstraat = new Street("Zonnebloemstraat", singletonList(aWayId(184159857L)));
        Street deurnemeers = new Street("Deurnemeers", singletonList(aWayId(34347705L)));
        Street adolphe = new Street("Adolphe Hullebroeckstraat", singletonList(aWayId(37071270L)));
        Street weerstandstraat = new Street("Weerstandstraat", singletonList(aWayId(37189933L)));
        Street stookt = new Street("Stooktstraat", asList(aWayId(37071108L), aWayId(37073481L)));
        Street langeweg = new Street("Langeweg", asList(aWayId(37071271L), aWayId(184159856L)));
        Street deurnestraat = new Street("Deurnestraat", asList(aWayId(121772348L), aWayId(121772349L), aWayId(161282474L)));
        return new Cluster("Langeweg", asList(isidoor, achturenstraat, zonnebloemstraat, deurnemeers, weerstandstraat, stookt, langeweg, deurnestraat, adolphe));
    }

    private Cluster aatstraat() {
        Street nijverheidsstraat = new Street("Nijverheidsstraat", singletonList(aWayId(166338313L)));
        Street hermes = new Street("Hermes Van Wynghenestraat", singletonList(aWayId(161056430L)));
        Street georges = new Street("Georges Desmetstraat", singletonList(aWayId(40029763L)));
        Street beukenlaan = new Street("Beukenlaan", singletonList(aWayId(37071177L)));
        Street aatstraat = new Street("Aatstraat", asList(aWayId(401951017L), aWayId(352639601L)));
        Street adolphe = new Street("Adolphe Hullebroeckstraat", asList(aWayId(37071176L), aWayId(37071269L)));
        Street ovide = new Street("Ovide Decrolylaan", asList(aWayId(148455816L), aWayId(226011553L)));
        return new Cluster("Aatstraat", asList(nijverheidsstraat, hermes, georges, beukenlaan, aatstraat, adolphe, ovide));
    }

    private Cluster scheldekouter() {
        Street scheldekouter = new Street("Scheldekouter", asList(aWayId(37073753L), aWayId(37073754L), aWayId(40032506L),
                aWayId(40032507L), aWayId(40032508L), aWayId(184171348L), aWayId(184171349L), aWayId(184171350L), aWayId(184171351L),
                aWayId(184171352L), aWayId(184171353L), aWayId(184171354L), aWayId(184171355L), aWayId(184171356L), aWayId(184171357L),
                aWayId(184171358L), aWayId(184171359L), aWayId(226011557L)));
        return new Cluster("Scheldekouter", singletonList(scheldekouter));
    }

    private Cluster elzelestraat() {
        Street poermagazijnstraat = new Street("Poermagazijnstraat", singletonList(aWayId(8590414L)));
        Street bredestraat = new Street("Bredestraat1", asList(aWayId(8117600L), aWayId(226011535L), aWayId(226011536L), aWayId(313880310L), aWayId(313880311L), aWayId(313880312L)));
        Street brugstraat = new Street("Nieuwe Brugstraat", singletonList(aWayId(8590347L)));
        Street groteMarijve = new Street("Grote Marijve", singletonList(aWayId(8590083L)));
        Street bareelstraat = new Street("Bareelstraat", singletonList(aWayId(8590086L)));
        Street zenobe = new Street("Zénobe Grammenestraat", singletonList(aWayId(8590346L)));
        Street olifantstraat = new Street("Olifantstraat", asList(aWayId(8117037L), aWayId(313880322L)));
        Street kleineMarijve = new Street("Kleine Marijve", asList(aWayId(184311574L), aWayId(392652889L)));
        Street elzelestraat = new Street("Elzelestraat", asList(aWayId(184838168L), aWayId(225977064L), aWayId(313880319L), aWayId(313880320L)));
        return new Cluster("Elzelestraat", asList(poermagazijnstraat, bredestraat, brugstraat, groteMarijve, bareelstraat, zenobe, olifantstraat, kleineMarijve, elzelestraat));
    }

    private Cluster elzeelsesteenweg() {
        Street europastraat = new Street("Europastraat", singletonList(aWayId(405716303L)));
        Street papekouters = new Street("Papekouters", singletonList(aWayId(37073782L)));
        Street konrad = new Street("Konrad Adenauerstraat", singletonList(aWayId(142111488L)));
        Street hendrik = new Street("Hendrik Consciencestraat", singletonList(aWayId(25772263L)));
        Street blauwesteen = new Street("Blauwesteen", asList(aWayId(8590415L), aWayId(132344727L), aWayId(184298288L)));
        Street elzeelsesteenweg = new Street("Elzeelsesteenweg", asList(aWayId(226011538L), aWayId(226011539L), aWayId(361112987L)));
        Street beylsstraat = new Street("Mgr. Beylsstraat", asList(aWayId(37073858L), aWayId(60788200L), aWayId(142112965L), aWayId(360015960L)));
        return new Cluster("Elzeelsesteenweg", asList(europastraat, papekouters, konrad, hendrik, blauwesteen, elzeelsesteenweg, beylsstraat));
    }

    private Cluster rotterij() {
        Street rodekruisstraat = new Street("Rodekruisstraat", singletonList(aWayId(157253693L)));
        Street jean = new Street("Jean Monnetstraat", singletonList(aWayId(132344720L)));
        Street paul = new Street("Paul Henri Spaakstraat", singletonList(aWayId(401928343L)));
        Street kersenstraat = new Street("Kersenstraat", singletonList(aWayId(157367578L)));
        Street ooststraat = new Street("Ooststraat", singletonList(aWayId(229046770L)));
        Street jagersstraat = new Street("Jagersstraat", singletonList(aWayId(127735634L)));
        Street waatsbrugstraat = new Street("Waatsbrugstraat", asList(aWayId(8590344L), aWayId(142111069L)));
        Street robert = new Street("Robert Schumanstraat", asList(aWayId(132344712L), aWayId(132344724L)));
        Street elzeelsesteenweg = new Street("Elzeelsesteenweg", asList(aWayId(184281893L), aWayId(184281894L), aWayId(400454956L)));
        Street rotterij = new Street("Rotterij", asList(aWayId(8590088L), aWayId(25772264L), aWayId(37073932L), aWayId(60788231L), aWayId(121772391L),
                aWayId(127735631L), aWayId(132344730L), aWayId(226011555L), aWayId(226011556L), aWayId(360015961L), aWayId(400480617L)));
        return new Cluster("Rotterij", asList(rodekruisstraat, jean, paul, kersenstraat, ooststraat, jagersstraat, waatsbrugstraat, robert, elzeelsesteenweg, rotterij));
    }

    private Cluster germinal() {
        Street germinal = new Street("Germinal", asList(aWayId(8590089L), aWayId(8590337L), aWayId(8590345L), aWayId(132344713L), aWayId(169175174L),
                aWayId(169175175L), aWayId(169175176L), aWayId(169175177L), aWayId(169175178L), aWayId(169175179L), aWayId(169175181L), aWayId(169175182L), aWayId(184742070L), aWayId(226011543L), aWayId(226011544L)));
        return new Cluster("Germinal", singletonList(germinal));
    }

    private Cluster maagdenstraat() {
        Street geraardsbergenstraat = new Street("Geraardsbergenstraat", singletonList(aWayId(33995137L)));
        Street boontjesstraat = new Street("Boontjesstraat", singletonList(aWayId(142111780L)));
        Street bossenberg = new Street("Bossenberg", singletonList(aWayId(403509760L)));
        Street waaienberg = new Street("Waaienberg", singletonList(aWayId(142110458L)));
        Street stijn = new Street("Stijn Streuvelsstraat", asList(aWayId(114457105L), aWayId(132344719L)));
        Street linde = new Street("Linde", asList(aWayId(40029765L), aWayId(40029766L)));
        Street lorettestraat = new Street("Lorettestraat", asList(aWayId(8590339L), aWayId(8590341L), aWayId(114457102L), aWayId(374137248L)));
        Street nachtegaalstraat = new Street("Nachtegaalstraat", asList(aWayId(114454246L), aWayId(184406096L), aWayId(184406097L), aWayId(314032242L)));
        Street maagdenstraat = new Street("Maagdenstraat", asList(aWayId(8590340L), aWayId(8590342L), aWayId(25772245L), aWayId(121772362L), aWayId(226011550L)));
        return new Cluster("Maagdenstraat", asList(geraardsbergenstraat, boontjesstraat, bossenberg, waaienberg, stijn, linde, lorettestraat, nachtegaalstraat, maagdenstraat));
    }

    private Cluster ninovestraat() {
        Street auguste = new Street("Auguste Delfossestraat", singletonList(aWayId(27548939L)));
        Street heckensveld = new Street("Heckensveld", singletonList(aWayId(157213215L)));
        Street linde = new Street("Linde", singletonList(aWayId(300350223L)));
        Street ruyffearekoer = new Street("Ruyffearekoer", singletonList(aWayId(349835821L)));
        Street napoleon = new Street("Napoléon Annicqstraat", singletonList(aWayId(37131853L)));
        Street theodule = new Street("Théodule Canfijnstraat", singletonList(aWayId(113873523L)));
        Street albert = new Street("Albert Massezstraat", singletonList(aWayId(351917917L)));
        Street drieborrebeekstraat = new Street("Drieborrebeekstraat", singletonList(aWayId(165581533L)));
        Street adolf = new Street("Adolf Demetsstraat", asList(aWayId(113873526L), aWayId(429432515L)));
        Street oscar = new Street("Oscar Delghuststraat", asList(aWayId(8117053L), aWayId(226011552L)));
        Street clementine = new Street("Clementine De Niestraat", asList(aWayId(169176673L), aWayId(169176676L), aWayId(182196213L)));
        Street gebroeders = new Street("Gebroeders Dopchiestraat", asList(aWayId(37131852L), aWayId(422053839L), aWayId(422053840L)));
        Street savooistraat = new Street("Savooistraat", asList(aWayId(86502117L), aWayId(422048220L), aWayId(422048221L)));
        Street ninovestraat = new Street("Ninovestraat", asList(aWayId(8117541L), aWayId(226139987L), aWayId(313880321L), aWayId(425922152L)));
        Street beekstraat = new Street("Beekstraat", asList(aWayId(25751040L), aWayId(37131346L), aWayId(121772346L), aWayId(422051919L), aWayId(422056653L), aWayId(422056654L), aWayId(422059404L)));
        return new Cluster("Ninovestraat", asList(auguste, heckensveld, linde, ruyffearekoer, napoleon, theodule, albert, drieborrebeekstraat, adolf, oscar, clementine, gebroeders, ninovestraat, beekstraat, savooistraat));
    }

    private Cluster dammekensstraat() {
        Street etienne = new Street("Etienne Verschuerenstraat", singletonList(aWayId(353263512L)));
        Street edmon = new Street("Edmon Van Beverenplein", singletonList(aWayId(25751038L)));
        Street karel = new Street("Karel Lodewijk Ledeganck", singletonList(aWayId(42834718L)));
        Street vuurkruisenstraat = new Street("Vuurkruisenstraat", singletonList(aWayId(25751039L)));
        Street dammekensstraat = new Street("Dammekensstraat", asList(aWayId(25751037L), aWayId(86502126L), aWayId(121772347L)));
        Street ommegangstraat = new Street("Ommegangstraat", asList(aWayId(11838834L), aWayId(116867729L), aWayId(121772366L), aWayId(141862360L), aWayId(165581543L), aWayId(228838173L), aWayId(313857548L), aWayId(313857549L), aWayId(369901001L), aWayId(377177489L)));
        return new Cluster("Dammekensstraat", asList(etienne, edmon, karel, vuurkruisenstraat, dammekensstraat, ommegangstraat));
    }

    private Cluster savooistraat() {
        Street drieborrebeekstraat = new Street("Drieborrebeekstraat", singletonList(aWayId(121772395L)));
        Street tenBergestraat = new Street("Ten Bergestraat", singletonList(aWayId(165581541L)));
        Street savooistraat = new Street("Savooistraat", asList(aWayId(40029767L), aWayId(86502110L), aWayId(121772375L), aWayId(121772376L), aWayId(157211440L), aWayId(361668734L), aWayId(361696710L), aWayId(369900881L), aWayId(369900882L)));
        return new Cluster("Savooistraat", asList(drieborrebeekstraat, savooistraat, tenBergestraat));
    }

    private Cluster ninoofsesteenweg() {
        Street mahautveld = new Street("Mahautveld", singletonList(aWayId(121772396L)));
        Street houtstraat = new Street("Houtstraat", singletonList(aWayId(369900287L)));
        Street prolstraat = new Street("Prolstraat", asList(aWayId(34006568L), aWayId(114457099L)));
        Street braambos = new Street("Braambos", asList(aWayId(60788185L), aWayId(396301313L)));
        Street fortuinberg = new Street("Fortuinberg", asList(aWayId(34006570L), aWayId(40032209L)));
        Street populierstraat = new Street("Populierstraat", asList(aWayId(121772392L), aWayId(393744947L), aWayId(400273002L)));
        Street bosrede = new Street("Bosrede", asList(aWayId(37901416L), aWayId(114457096L), aWayId(369900757L)));
        Street boekzitting = new Street("Boekzitting", asList(aWayId(86502109L), aWayId(221228789L), aWayId(361702577L)));
        Street muziekbosstraat = new Street("Muziekbosstraat", asList(aWayId(40029769L), aWayId(184231331L), aWayId(184231332L), aWayId(184231333L)));
        Street hul = new Street("Hul", asList(aWayId(151500959L), aWayId(184568218L), aWayId(422045371L), aWayId(422045372L)));
        Street iJsmolenstraat = new Street("IJsmolenstraat", asList(aWayId(34006545L), aWayId(37901414L), aWayId(114457095L), aWayId(121772356L)));
        Street kanarieberg = new Street("Kanarieberg", asList(aWayId(11837293L), aWayId(86502116L), aWayId(86502136L), aWayId(86502145L), aWayId(184230479L)));
        Street ninoofsesteenweg = new Street("Ninoofsesteenweg", asList(aWayId(155422059L), aWayId(314032243L), aWayId(314032245L), aWayId(314032246L),
                aWayId(314032247L), aWayId(314032249L), aWayId(314032250L), aWayId(350951604L), aWayId(361710226L), aWayId(422041559L), aWayId(422041567L)));
        return new Cluster("Ninoofsesteenweg", asList(mahautveld, houtstraat, prolstraat, braambos, fortuinberg, populierstraat, bosrede, boekzitting, muziekbosstraat, hul, iJsmolenstraat, kanarieberg, ninoofsesteenweg));
    }

    private Cluster maquisstraat() {
        Street temseveldstraat = new Street("Temseveldstraat", singletonList(aWayId(121991190L)));
        Street tombelle = new Street("Tombelle", singletonList(aWayId(37071725L)));
        Street brokelaerestraat = new Street("Brokelaerestraat", singletonList(aWayId(34952682L)));
        Street hoogdeurnestraat = new Street("Hoogdeurnestraat", singletonList(aWayId(44774062L)));
        Street vinkenstraat = new Street("Vinkenstraat", singletonList(aWayId(166338139L)));
        Street heide = new Street("Kleine Heide", asList(aWayId(37071724L), aWayId(402521243L)));
        Street albert = new Street("Albert Demanezstraat", asList(aWayId(37072187L), aWayId(78240372L)));
        Street elzeelsesteenweg = new Street("Elzeelsesteenweg", asList(aWayId(33993757L), aWayId(221233963L), aWayId(221233964L)));
        Street maquisstraat = new Street("Maquisstraat", asList(aWayId(35584645L), aWayId(37071722L), aWayId(37071723L), aWayId(121772363L)));
        return new Cluster("Maquisstraat", asList(temseveldstraat, tombelle, brokelaerestraat, hoogdeurnestraat, vinkenstraat, heide, albert, elzeelsesteenweg, maquisstraat));
    }
}
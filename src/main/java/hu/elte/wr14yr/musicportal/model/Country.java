package hu.elte.wr14yr.musicportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "COUNTRIES")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "ICON_FILE_GDA_ID")
    private String iconFileGdaId;

    @OneToMany(targetEntity = User.class, mappedBy = "countryId")
    private List<User> users;

    public static Map<String, String> countries;
    static {
        countries = new HashMap<>();
        countries.put("Afganisztán", "");
        countries.put("Albánia", "");
        countries.put("Algéria", "");
        countries.put("Amerikai Egyesült Államok", "");
        countries.put("Andorra", "");
        countries.put("Argentína", "");
        countries.put("Ausztrália", "");
        countries.put("Ausztria", "");
        countries.put("Azerbajdzsán", "");
        countries.put("Bahama-szigetek", "");
        countries.put("Banglades", "");
        countries.put("Belgium", "");
        countries.put("Benin", "");
        countries.put("Bolívia", "");
        countries.put("Bosznia-Hercegovina", "");
        countries.put("Brazília", "");
        countries.put("Bulgária", "");
        countries.put("Burundi", "");
        countries.put("Ciprus", "");
        countries.put("Csád", "");
        countries.put("Csehország", "");
        countries.put("Dánia", "");
        countries.put("Dél-Afrika", "");
        countries.put("Dél-Korea", "");
        countries.put("Dél-Szudán", "");
        countries.put("Dominikai Közösség", "");
        countries.put("Dominikai Köztársaság", "");
        countries.put("Dzsibuti", "");
        countries.put("Egyesült Arab Emírségek", "");
        countries.put("Egyesült Királyság", "");
        countries.put("Egyiptom", "");
        countries.put("Elefántcsontpart", "");
        countries.put("Eritrea", "");
        countries.put("Észak-Korea", "");
        countries.put("Észtország", "");
        countries.put("Etiópia", "");
        countries.put("Fehéroroszország", "");
        countries.put("Fidzsi-szigetek", "");
        countries.put("Finnország", "");
        countries.put("Franciaország", "");
        countries.put("Fülöp-szigetek", "");
        countries.put("Gabon", "");
        countries.put("Gambia", "");
        countries.put("Görögország", "");
        countries.put("Grenada", "");
        countries.put("Grúzia", "");
        countries.put("Haiti", "");
        countries.put("Hollandia", "");
        countries.put("Horvátország", "");
        countries.put("India", "");
        countries.put("Indonézia", "");
        countries.put("Irak", "");
        countries.put("Irán", "");
        countries.put("Írország", "");
        countries.put("Izland", "");
        countries.put("Izrael", "");
        countries.put("Japán", "");
        countries.put("Jemen", "");
        countries.put("Jordánia", "");
        countries.put("Kambodzsa", "");
        countries.put("Kamerun", "");
        countries.put("Kanada", "");
        countries.put("Katar", "");
        countries.put("Kazahsztán", "");
        countries.put("Kelet-Timor", "");
        countries.put("Kenya", "");
        countries.put("Kína", "");
        countries.put("Kirgizisztán", "");
        countries.put("Kiribati", "");
        countries.put("Kolumbia", "");
        countries.put("Kongói Demokratikus Köztársaság", "");
        countries.put("Kongói Köztársaság", "");
        countries.put("Közép-Afrika", "");
        countries.put("Kuba", "");
        countries.put("Laosz", "");
        countries.put("Lengyelország", "");
        countries.put("Lettország", "");
        countries.put("Libanon", "");
        countries.put("Libéria", "");
        countries.put("Líbia", "");
        countries.put("Litvánia", "");
        countries.put("Luxemburg", "");
        countries.put("Macedónia", "");
        countries.put("Madagaszkár", "");
        countries.put("Magyarország", "");
        countries.put("Maldív-szigetek", "");
        countries.put("Mali", "");
        countries.put("Málta", "");
        countries.put("Marokkó", "");
        countries.put("Mauritánia", "");
        countries.put("Mexikó", "");
        countries.put("Mianmar", "");
        countries.put("Mikronézia", "");
        countries.put("Moldova", "");
        countries.put("Mongólia", "");
        countries.put("Montenegró", "");
        countries.put("Mozambik", "");
        countries.put("Namíbia", "");
        countries.put("Nauru", "");
        countries.put("Németország", "");
        countries.put("Nepál", "");
        countries.put("Niger", "");
        countries.put("Nigéria", "");
        countries.put("Norvégia", "");
        countries.put("Olaszország", "");
        countries.put("Omán", "");
        countries.put("Örményország", "");
        countries.put("Oroszország", "");
        countries.put("Pakisztán", "");
        countries.put("Palau", "");
        countries.put("Panama", "");
        countries.put("Pápua Új-Guinea", "");
        countries.put("Peru", "");
        countries.put("Portugália", "");
        countries.put("Románia", "");
        countries.put("Ruanda", "");
        countries.put("Salamon-szigetek", "");
        countries.put("Spanyolország", "");
        countries.put("Srí Lanka", "");
        countries.put("Svájc", "");
        countries.put("Svédország", "");
        countries.put("Szamoa", "");
        countries.put("Szaúd-Arábia", "");
        countries.put("Szenegál", "");
        countries.put("Szerbia", "");
        countries.put("Szingapúr", "");
        countries.put("Szíria", "");
        countries.put("Szlovákia", "");
        countries.put("Szlovénia", "");
        countries.put("Szomália", "");
        countries.put("Szudán", "");
        countries.put("Szváziföld", "");
        countries.put("Tádzsikisztán", "");
        countries.put("Tanzánia", "");
        countries.put("Tonga", "");
        countries.put("Törökország", "");
        countries.put("Tunézia", "");
        countries.put("Türkmenisztán", "");
        countries.put("Tuvalu", "");
        countries.put("Uganda", "");
        countries.put("Új-Zéland", "");
        countries.put("Ukrajna", "");
        countries.put("Üzbegisztán", "");
        countries.put("Vanuatu", "");
        countries.put("Venezuela", "");
        countries.put("Zambia", "");
        countries.put("Zöld-foki-szigetek", "");
    }
}
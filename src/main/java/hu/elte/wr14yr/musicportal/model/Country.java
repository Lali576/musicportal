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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "ICON_FILE_URL")
    private String iconFileURL;

    @OneToMany(targetEntity = User.class, mappedBy = "countryId")
    private List<User> users;

    public static Map<String, String> countries = new HashMap<>();
    static {
        countries.put("Afganisztán", "af/shiny/32.png");
        countries.put("Albánia", "al/shiny/32.png");
        countries.put("Amerikai Egyesült Államok", "us/shiny/32.png");
        countries.put("Argentína", "ar/shiny/32.png");
        countries.put("Ausztrália", "au/shiny/32.png");
        countries.put("Ausztria", "at/shiny/32.png");
        countries.put("Azerbajdzsán", "az/shiny/32.png");
        countries.put("Belgium", "be/shiny/32.png");
        countries.put("Brazília", "br/shiny/32.png");
        countries.put("Bulgária", "bg/shiny/32.png");
        countries.put("Ciprus", "cy/shiny/32.png");
        countries.put("Csehország", "cz/shiny/32.png");
        countries.put("Dánia", "dk/shiny/32.png");
        countries.put("Dél-Korea", "kr/shiny/32.png");
        countries.put("Egyesült Arab Emírségek", "ae/shiny/32.png");
        countries.put("Egyesült Királyság", "gb/shiny/32.png");
        countries.put("Egyiptom", "eg/shiny/32.png");
        countries.put("Észtország", "ee/shiny/32.png");
        countries.put("Fehéroroszország", "by/shiny/32.png");
        countries.put("Finnország", "fi/shiny/32.png");
        countries.put("Franciaország", "fr/shiny/32.png");
        countries.put("Görögország", "gr/shiny/32.png");
        countries.put("Grúzia", "ge/shiny/32.png");
        countries.put("Hollandia", "nl/shiny/32.png");
        countries.put("Horvátország", "hr/shiny/32.png");
        countries.put("India", "in/shiny/32.png");
        countries.put("Indonézia", "id/shiny/32.png");
        countries.put("Irak", "iq/shiny/32.png");
        countries.put("Irán", "ir/shiny/32.png");
        countries.put("Írország", "ie/shiny/32.png");
        countries.put("Izland", "is/shiny/32.png");
        countries.put("Izrael", "il/shiny/32.png");
        countries.put("Japán", "jp/shiny/32.png");
        countries.put("Kanada", "ca/shiny/32.png");
        countries.put("Katar", "qa/shiny/32.png");
        countries.put("Kazahsztán", "kz/shiny/32.png");
        countries.put("Kína", "cn/shiny/32.png");
        countries.put("Kolumbia", "co/shiny/32.png");
        countries.put("Kuba", "cu/shiny/32.png");
        countries.put("Lengyelország", "pl/shiny/32.png");
        countries.put("Lettország", "lv/shiny/32.png");
        countries.put("Litvánia", "lt/shiny/32.png");
        countries.put("Luxemburg", "lu/shiny/32.png");
        countries.put("Macedónia", "mk/shiny/32.png");
        countries.put("Magyarország", "hu/shiny/32.png");
        countries.put("Malájzia", "my/shiny/32.png");
        countries.put("Málta", "mt/shiny/32.png");
        countries.put("Mexikó", "mx/shiny/32.png");
        countries.put("Moldova", "md/shiny/32.png");
        countries.put("Mongólia", "mn/shiny/32.png");
        countries.put("Montenegró", "me/shiny/32.png");
        countries.put("Németország", "de/shiny/32.png");
        countries.put("Norvégia", "no/shiny/32.png");
        countries.put("Olaszország", "it/shiny/32.png");
        countries.put("Örményország", "am/shiny/32.png");
        countries.put("Oroszország", "ru/shiny/32.png");
        countries.put("Pakisztán", "pk/shiny/32.png");
        countries.put("Peru", "pe/shiny/32.png");
        countries.put("Portugália", "pt/shiny/32.png");
        countries.put("Románia", "ro/shiny/32.png");
        countries.put("Spanyolország", "es/shiny/32.png");
        countries.put("Svájc", "ch/shiny/32.png");
        countries.put("Svédország", "se/shiny/32.png");
        countries.put("Szaúd-Arábia", "sa/shiny/32.png");
        countries.put("Szerbia", "rs/shiny/32.png");
        countries.put("Szingapúr", "sg/shiny/32.png");
        countries.put("Szíria", "sy/shiny/32.png");
        countries.put("Szlovákia", "sk/shiny/32.png");
        countries.put("Szlovénia", "sl/shiny/32.png");
        countries.put("Törökország", "tr/shiny/32.png");
        countries.put("Új-Zéland", "nz/shiny/32.png");
        countries.put("Ukrajna", "ua/shiny/32.png");
    }
}
package viking.vikingtask02.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CSVDataModel {
    String Country,City,Date;

    public CSVDataModel(String country, String city, String date) {
        Country = country;
        City = city;
        Date = date;
    }
}

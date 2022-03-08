package viking.vikingtask02.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import viking.vikingtask02.Models.Hotel.HotelsInfos;

@Data
@NoArgsConstructor
public class CityHotelModel {
    String CityName;
    HotelsInfos Hotels;

    public CityHotelModel(String cityName, HotelsInfos hotels) {
        CityName = cityName;
        Hotels = hotels;
    }
}

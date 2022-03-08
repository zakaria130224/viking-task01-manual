package viking.vikingtask02.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import viking.vikingtask02.Models.Covid.CovidInfo;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement
public class XMLResModel {

    String Country;

    String Date;

    CityHotelModel HotelsInCity;

    CovidInfo CovidInfo;

    public XMLResModel(String country) {
        Country = country;
    }
}

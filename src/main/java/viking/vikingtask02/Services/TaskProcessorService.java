package viking.vikingtask02.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import viking.vikingtask02.Models.CSVDataModel;
import viking.vikingtask02.Models.CityHotelModel;
import viking.vikingtask02.Models.Covid.CovidInfo;
import viking.vikingtask02.Models.Hotel.HotelDetails;
import viking.vikingtask02.Models.Hotel.HotelsInfos;
import viking.vikingtask02.Models.XMLResModel;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskProcessorService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private HotelService hotelService;

    @Autowired
    private CovidAPIService covidAPIService;

    public XMLResModel getDataByCountry(CSVDataModel dataModel) throws IOException {
        XMLResModel xmlResModel=new XMLResModel();
        xmlResModel.setCountry(dataModel.getCountry());
        xmlResModel.setDate(dataModel.getDate());

        try {
            List<HotelDetails> hotels=hotelService.getHotelsByCity(dataModel.getCity());
            xmlResModel.setHotelsInCity(new CityHotelModel(dataModel.getCity(),new HotelsInfos(hotels)));

        }catch (Exception e){
            logger.error("Failed on gathering hotel info: {}",e.getMessage());
        }

        try {
            CovidInfo covid=covidAPIService.getCovidByCountry(dataModel.getCountry(), dataModel.getDate());
            xmlResModel.setCovidInfo(covid);
            //xmlResModel.setCovidInfo(new CovidInfo("4822","6455","545","566","100","51"));

        }catch (Exception e){
            logger.error("Failed on gathering covid info: {}",e.getMessage());
        }

        return xmlResModel;
    }
}

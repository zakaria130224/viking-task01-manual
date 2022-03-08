package viking.vikingtask02.Services;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import viking.vikingtask02.Models.Covid.CovidInfo;
import viking.vikingtask02.Models.HTTPClientResponse;

import java.io.IOException;

@Service
public class CovidAPIService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Value("${covid.api.baseurl}")
    private String baseUrl;

    @Value("${covid.api.apihost}")
    private String apihost;

    @Value("${covid.api.apikey}")
    private String apikey;

    @Autowired
    private WebClientService webClientService;

    public CovidInfo getCovidByCountry(String country, String date) {


        try {
            HTTPClientResponse resCovid=webClientService.get(baseUrl.replace("#COUNTRY",country).replace("#DATE",date),apihost,apikey);
            Gson gson=new Gson();
            CovidInfo covid_data=gson.fromJson(resCovid.getDatalist().get(0).toString(), CovidInfo.class);
            System.out.println(covid_data);
            return covid_data;
        }catch (Exception e){
            logger.error(e.getMessage());
            return new CovidInfo();
        }

    }
}

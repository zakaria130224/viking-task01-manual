package viking.vikingtask02.ConfCamel;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import viking.vikingtask02.Models.CSVDataModel;
import viking.vikingtask02.Models.Country;
import viking.vikingtask02.Services.CSVFileReaderService;

import java.util.ArrayList;
import java.util.List;


@Service
public class CamelProcessor implements Processor {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private CSVFileReaderService cvsFileReaderSrvice;


    private List<CSVDataModel> list=new ArrayList<>();

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);

        list=cvsFileReaderSrvice.getDataFromCSV(body);
    }

    public List<CSVDataModel> getlist() {
        return list;
    }

    public List<Country> getCountrylist() {
        List<Country> countries =new ArrayList<>();
        list.forEach(x->{
            countries.add(new Country(x.getCountry()));
        });
        return countries;
    }
}

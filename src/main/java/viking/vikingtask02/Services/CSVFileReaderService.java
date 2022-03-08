package viking.vikingtask02.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import viking.vikingtask02.Models.CSVDataModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class CSVFileReaderService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    public List<CSVDataModel> getDataFromCSV(String loc){


        List<CSVDataModel> list=new ArrayList<>();
        String[] line=loc.split("\n");
        for (String ln:line) {
            String[] arr= ln.split(",");
            try {

                System.out.println(loc);
                list.add(new CSVDataModel(arr[0],arr[1],arr[2]));
            }catch (Exception e)
            {
                logger.error(e.getMessage());
            }

        }

        list.remove(0);
        return list;




    }

}

package viking.vikingtask02.Controllers;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viking.vikingtask02.ConfCamel.CamelProcessor;
import viking.vikingtask02.ConfCamel.TestCamelRoute;
import viking.vikingtask02.Models.CSVDataModel;
import viking.vikingtask02.Models.Country;
import viking.vikingtask02.Models.XMLResModel;
import viking.vikingtask02.Services.TaskProcessorService;
import viking.vikingtask02.Services.XMLWriterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    TestCamelRoute camelRoute;

    @Autowired
    CamelProcessor camelProcessor;

    @Autowired
    TaskProcessorService taskProcessorService;

    @Autowired
    XMLWriterService xmlWriterService;

    @GetMapping("/")
    public String showHome(Model model) throws Exception {

        CamelContext _ctx = new DefaultCamelContext();
        _ctx.addRoutes(camelRoute.createRouteBuilder());
        _ctx.setTracing(true);
        _ctx.start();

        Thread.sleep(2000);

        List<Country> countries = camelProcessor.getCountrylist();
        model.addAttribute("countries", countries);
        _ctx.stop();
        return "data_form";
    }

    @PostMapping("/show")
    public String displaydata(@ModelAttribute CSVDataModel req, Model model) throws IOException {

        XMLResModel xmlResModel= taskProcessorService.getDataByCountry(req);
        model.addAttribute("data", xmlResModel);
        return "display_form";
    }

    @GetMapping("/export/{country}/{city}/{date}")
    public void exportXMLBySelection(@PathVariable String country, @PathVariable String city, @PathVariable String date, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

        XMLResModel xmlResModel= taskProcessorService.getDataByCountry(new CSVDataModel(country,city,date));

        String res=xmlWriterService.jaxbObjectToXML(xmlResModel);
        // Download section
        response.setContentType(MediaType.APPLICATION_RSS_XML_VALUE);
        String reportFileName =xmlResModel.getCountry()+".xml";
        response.setHeader("Content-Disposition", String.format("attachment; filename="+reportFileName));

        IOUtils.copyLarge(new ByteArrayInputStream(res.getBytes()),response.getOutputStream());


    }

}

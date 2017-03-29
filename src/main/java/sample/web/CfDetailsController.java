package sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CfDetailsController {
    @Autowired
    private Environment environment;

    @RequestMapping(path={"/", "/cfdetails"})
    public String vcapProperties(Model model) {
        model.addAttribute("cfapp", environment.getProperty("VCAP_APPLICATION", "{}"));
        model.addAttribute("cfservices", environment.getProperty("VCAP_SERVICES", "{}"));
        model.addAttribute("cfenvs", System.getenv());
        model.addAttribute("activeProfiles", environment.getActiveProfiles());
        return "index";
    }

}
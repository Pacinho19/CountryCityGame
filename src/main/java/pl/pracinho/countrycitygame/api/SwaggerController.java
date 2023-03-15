package pl.pracinho.countrycitygame.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pracinho.countrycitygame.config.EndpointsConfig;

@RequestMapping((EndpointsConfig.API_PREFIX))
@Controller
public class SwaggerController {

    @GetMapping
    public String swaggerHome(){
        return "redirect:/swagger-ui.html";
    }
}

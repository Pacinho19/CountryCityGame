package pl.pracingo.countrycitygame.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pracingo.countrycitygame.config.EndpointsConfig;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping
    public String home2(){
        return "redirect:" + EndpointsConfig.UI_PREFIX;
    }
}

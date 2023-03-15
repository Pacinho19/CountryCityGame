package pl.pracinho.countrycitygame.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pracinho.countrycitygame.config.EndpointsConfig;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.service.CountryService;

@RequestMapping(EndpointsConfig.API_COUNTRY)
@RestController
public class CountryApiController extends SimpleParamController<Country, CountryService> {
}

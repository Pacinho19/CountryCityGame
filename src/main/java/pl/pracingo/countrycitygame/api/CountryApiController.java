package pl.pracingo.countrycitygame.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pracingo.countrycitygame.config.EndpointsConfig;
import pl.pracingo.countrycitygame.model.entity.City;
import pl.pracingo.countrycitygame.model.entity.Country;
import pl.pracingo.countrycitygame.service.CityService;
import pl.pracingo.countrycitygame.service.CountryService;

@RequestMapping(EndpointsConfig.API_COUNTRY)
@RestController
public class CountryApiController extends SimpleParamController<Country, CountryService> {
}

package pl.pracingo.countrycitygame.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pracingo.countrycitygame.config.EndpointsConfig;
import pl.pracingo.countrycitygame.model.entity.City;
import pl.pracingo.countrycitygame.service.CityService;

@RequestMapping(EndpointsConfig.API_CITY)
@RestController
public class CityApiController  extends SimpleParamController<City, CityService> {
}

package pl.pracinho.countrycitygame.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pracinho.countrycitygame.config.EndpointsConfig;
import pl.pracinho.countrycitygame.model.entity.City;
import pl.pracinho.countrycitygame.service.CityService;

@RequestMapping(EndpointsConfig.API_CITY)
@RestController
public class CityApiController  extends SimpleParamController<City, CityService> {
}

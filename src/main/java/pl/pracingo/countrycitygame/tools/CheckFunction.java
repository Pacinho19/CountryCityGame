package pl.pracingo.countrycitygame.tools;

import org.springframework.stereotype.Component;
import pl.pracingo.countrycitygame.service.CityService;
import pl.pracingo.countrycitygame.service.CountryService;

import java.util.function.Function;

@Component
public class CheckFunction {

    private CityService cityService;
    private CountryService countryService;
    private static CheckFunction instance;

    public CheckFunction(CityService cityService, CountryService countryService) {
        this.cityService = cityService;
        this.countryService = countryService;
        instance = this;
    }

    public static CheckFunction getInstance() {
        return instance;
    }

    public final Function<String, Boolean> COUNTRY = name -> countryService.existsByName(name);
    public final Function<String, Boolean> CITY = name -> cityService.existsByName(name);
}

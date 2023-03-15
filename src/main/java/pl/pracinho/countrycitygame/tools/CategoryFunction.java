package pl.pracinho.countrycitygame.tools;

import org.springframework.stereotype.Component;
import pl.pracinho.countrycitygame.model.entity.Animal;
import pl.pracinho.countrycitygame.model.entity.City;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.model.entity.Name;
import pl.pracinho.countrycitygame.service.AnimalService;
import pl.pracinho.countrycitygame.service.CityService;
import pl.pracinho.countrycitygame.service.CountryService;
import pl.pracinho.countrycitygame.service.NameService;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Component
public class CategoryFunction {

    private CityService cityService;
    private CountryService countryService;
    private AnimalService animalService;
    private NameService nameService;
    private static CategoryFunction instance;

    public CategoryFunction(CityService cityService, CountryService countryService, AnimalService animalService, NameService nameService) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.animalService = animalService;
        this.nameService = nameService;
        instance = this;
    }

    public static CategoryFunction getInstance() {
        return instance;
    }

    public final Function<String, Boolean> SEARCH_COUNTRY = name -> {
        Optional<Country> opt = countryService.findByName(name);
        if (opt.isEmpty()) return null;

        Country country = opt.get();
        return country.getCorrect() == null || country.getCorrect();
    };

    public final Function<String, Boolean> SEARCH_CITY = name -> {
        Optional<City> opt = cityService.findByName(name);
        if (opt.isEmpty()) return null;

        City city = opt.get();
        return city.getCorrect() == null || city.getCorrect();
    };

    public final Function<String, Boolean> SEARCH_ANIMAL = name -> {
        Optional<Animal> opt = animalService.findByName(name);
        if (opt.isEmpty()) return null;

        Animal animal = opt.get();
        return animal.getCorrect() == null || animal.getCorrect();
    };

    public final Function<String, Boolean> SEARCH_NAME = nameS -> {
        Optional<Name> opt = nameService.findByName(nameS);
        if (opt.isEmpty()) return null;

        Name name = opt.get();
        return name.getCorrect() == null || name.getCorrect();
    };

    public final BiConsumer<String, Boolean> SAVE_CITY = (value, correct) -> cityService.save(new City(value, correct));
    public final BiConsumer<String, Boolean> SAVE_COUNTRY = (value, correct) -> countryService.save(new Country(value, correct));
    public final BiConsumer<String, Boolean> SAVE_ANIMAL = (value, correct) -> animalService.save(new Animal(value, correct));
    public final BiConsumer<String, Boolean> SAVE_NAME = (value, correct) -> nameService.save(new Name(value, correct));

}
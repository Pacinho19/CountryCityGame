package pl.pracinho.countrycitygame.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pracinho.countrycitygame.model.entity.Animal;
import pl.pracinho.countrycitygame.model.entity.City;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.model.entity.Name;
import pl.pracinho.countrycitygame.repository.AnimalRepository;
import pl.pracinho.countrycitygame.repository.CityRepository;
import pl.pracinho.countrycitygame.repository.CountryRepository;
import pl.pracinho.countrycitygame.repository.NameRepository;
import pl.pracinho.countrycitygame.utils.FileUtils;

@RequiredArgsConstructor
@Component
public class StartApp {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AnimalRepository animalRepository;
    private final NameRepository nameRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        initCountries();
        initCity();
        initAnimals();
        initNames();
    }

    private void initNames() {
        if (nameRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Name.txt"))
                .forEach(name -> nameRepository.save(
                        new Name(name, null)
                ));
    }

    private void initAnimals() {
        if (animalRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Animal.txt"))
                .forEach(name -> animalRepository.save(
                        new Animal(name, null)
                ));
    }

    private void initCity() {
        if (cityRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/City.txt"))
                .forEach(name -> cityRepository.save(
                        new City(name, null)
                ));
    }

    private void initCountries() {
        if (countryRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Country.txt"))
                .forEach(name -> countryRepository.save(
                        new Country(name, null)
                ));

    }
}
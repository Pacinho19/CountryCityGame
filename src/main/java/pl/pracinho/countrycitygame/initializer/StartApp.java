package pl.pracinho.countrycitygame.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pracinho.countrycitygame.model.entity.*;
import pl.pracinho.countrycitygame.repository.*;
import pl.pracinho.countrycitygame.utils.FileUtils;

@RequiredArgsConstructor
@Component
public class StartApp {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AnimalRepository animalRepository;
    private final NameRepository nameRepository;
    private final ThingRepository thingRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        initCountries();
        initCity();
        initAnimals();
        initNames();
        initThing();
    }

    private void initThing() {
        if (thingRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Thing.txt"))
                .stream()
                .distinct()
                .forEach(name -> thingRepository.save(
                        new Thing(name, null)
                ));
    }

    private void initNames() {
        if (nameRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Name.txt"))
                .stream()
                .distinct()
                .forEach(name -> nameRepository.save(
                        new Name(name, null)
                ));
    }

    private void initAnimals() {
        if (animalRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Animal.txt"))
                .stream()
                .distinct()
                .forEach(name -> animalRepository.save(
                        new Animal(name, null)
                ));
    }

    private void initCity() {
        if (cityRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/City.txt"))
                .stream()
                .distinct()
                .forEach(name -> cityRepository.save(
                        new City(name, null)
                ));
    }

    private void initCountries() {
        if (countryRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Country.txt"))
                .stream()
                .distinct()
                .forEach(name -> countryRepository.save(
                        new Country(name, null)
                ));

    }
}
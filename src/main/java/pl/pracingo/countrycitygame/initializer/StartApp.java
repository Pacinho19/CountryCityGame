package pl.pracingo.countrycitygame.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.pracingo.countrycitygame.model.entity.Country;
import pl.pracingo.countrycitygame.repository.CountryRepository;
import pl.pracingo.countrycitygame.utils.FileUtils;

import java.io.File;

@RequiredArgsConstructor
@Component
public class StartApp {

    private final CountryRepository countryRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        initCountries();
    }

    private void initCountries() {
        if (countryRepository.count() > 0) return;

        FileUtils.readTxt(FileUtils.getFileFromResource("dictionary/Country.txt"))
                .forEach(name -> countryRepository.save(
                        new Country(name)
                ));

    }
}
package pl.pracingo.countrycitygame.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.repository.CountryRepository;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public boolean existsByName(String name) {
        return countryRepository.existsByName(name);
    }
}
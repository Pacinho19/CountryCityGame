package pl.pracinho.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.repository.CountryRepository;

@Service
public class CountryService extends SimpleParamService<Country, CountryRepository> {
}
package pl.pracingo.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.model.entity.City;
import pl.pracingo.countrycitygame.model.entity.Country;
import pl.pracingo.countrycitygame.repository.CityRepository;
import pl.pracingo.countrycitygame.repository.CountryRepository;

@Service
public class CountryService extends SimpleParamService<Country, CountryRepository> {
}
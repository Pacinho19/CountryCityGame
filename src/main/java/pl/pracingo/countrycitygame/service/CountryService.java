package pl.pracingo.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracingo.countrycitygame.model.entity.City;
import pl.pracingo.countrycitygame.repository.CityRepository;

@Service
public class CountryService extends SimpleParamService<City, CityRepository> {
}
package pl.pracinho.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.entity.City;
import pl.pracinho.countrycitygame.repository.CityRepository;

@Service
public class CityService extends SimpleParamService<City, CityRepository> {

}
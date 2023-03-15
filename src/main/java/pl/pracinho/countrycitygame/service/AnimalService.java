package pl.pracinho.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.entity.Animal;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.repository.AnimalRepository;
import pl.pracinho.countrycitygame.repository.CountryRepository;

@Service
public class AnimalService extends SimpleParamService<Animal, AnimalRepository> {
}
package pl.pracinho.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.model.entity.Thing;
import pl.pracinho.countrycitygame.repository.CountryRepository;
import pl.pracinho.countrycitygame.repository.ThingRepository;

@Service
public class ThingService extends SimpleParamService<Thing, ThingRepository> {
}
package pl.pracinho.countrycitygame.service;

import org.springframework.stereotype.Service;
import pl.pracinho.countrycitygame.model.entity.Animal;
import pl.pracinho.countrycitygame.model.entity.Name;
import pl.pracinho.countrycitygame.repository.AnimalRepository;
import pl.pracinho.countrycitygame.repository.NameRepository;

@Service
public class NameService extends SimpleParamService<Name, NameRepository> {
}
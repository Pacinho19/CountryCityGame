package pl.pracinho.countrycitygame.repository;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.model.entity.Animal;

@Repository
public interface AnimalRepository extends SimpleParamRepository<Animal, Long>{

}
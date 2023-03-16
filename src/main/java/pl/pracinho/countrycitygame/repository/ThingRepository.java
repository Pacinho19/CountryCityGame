package pl.pracinho.countrycitygame.repository;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.model.entity.Country;
import pl.pracinho.countrycitygame.model.entity.Thing;

@Repository
public interface ThingRepository extends SimpleParamRepository<Thing, Long>{

}
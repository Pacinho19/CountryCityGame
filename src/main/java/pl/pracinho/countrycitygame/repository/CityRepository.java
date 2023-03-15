package pl.pracinho.countrycitygame.repository;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.model.entity.City;

@Repository
public interface CityRepository extends SimpleParamRepository<City, Long>{

}
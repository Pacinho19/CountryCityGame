package pl.pracingo.countrycitygame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pracingo.countrycitygame.model.entity.City;
import pl.pracingo.countrycitygame.model.entity.Country;

@Repository
public interface CityRepository extends SimpleParamRepository<City, Long>{

}
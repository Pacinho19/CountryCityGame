package pl.pracinho.countrycitygame.repository;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.model.entity.Country;

@Repository
public interface CountryRepository extends SimpleParamRepository<Country, Long>{

}
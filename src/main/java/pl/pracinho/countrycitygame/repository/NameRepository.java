package pl.pracinho.countrycitygame.repository;

import org.springframework.stereotype.Repository;
import pl.pracinho.countrycitygame.model.entity.Animal;
import pl.pracinho.countrycitygame.model.entity.Name;

@Repository
public interface NameRepository extends SimpleParamRepository<Name, Long>{

}
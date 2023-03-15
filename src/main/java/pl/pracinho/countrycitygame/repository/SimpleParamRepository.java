package pl.pracinho.countrycitygame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.pracinho.countrycitygame.model.entity.parent.MyEntity;

import java.util.Optional;

@NoRepositoryBean
public interface SimpleParamRepository<T extends MyEntity, ID> extends JpaRepository<T, ID> {

    boolean existsByName(String name);

    Optional<T> findFirstByName(String toUpperCase);
}

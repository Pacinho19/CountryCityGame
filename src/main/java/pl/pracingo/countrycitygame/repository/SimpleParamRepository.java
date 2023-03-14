package pl.pracingo.countrycitygame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.pracingo.countrycitygame.model.entity.parent.MyEntity;

@NoRepositoryBean
public interface SimpleParamRepository<T extends MyEntity,ID> extends JpaRepository<T,ID> {

    boolean existsByName(String name);
}

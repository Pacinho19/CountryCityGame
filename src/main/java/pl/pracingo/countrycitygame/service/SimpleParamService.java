package pl.pracingo.countrycitygame.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.pracingo.countrycitygame.model.entity.parent.MyEntity;
import pl.pracingo.countrycitygame.repository.SimpleParamRepository;

public abstract class SimpleParamService<T extends MyEntity, R extends SimpleParamRepository> {

    @Autowired
    private R r;

    public boolean existsByName(String name) {
        if(name==null) return false;
        return r.existsByName(name.toUpperCase());
    }

}

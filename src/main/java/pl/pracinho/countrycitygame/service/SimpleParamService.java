package pl.pracinho.countrycitygame.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.pracinho.countrycitygame.model.entity.parent.MyEntity;
import pl.pracinho.countrycitygame.repository.SimpleParamRepository;

public abstract class SimpleParamService<T extends MyEntity, R extends SimpleParamRepository> {

    @Autowired
    private R r;

    public boolean existsByName(String name) {
        if(name==null) return false;
        return r.existsByName(name.toUpperCase());
    }

}

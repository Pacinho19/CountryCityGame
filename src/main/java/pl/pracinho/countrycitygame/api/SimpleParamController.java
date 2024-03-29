package pl.pracinho.countrycitygame.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pracinho.countrycitygame.model.entity.parent.MyEntity;
import pl.pracinho.countrycitygame.service.SimpleParamService;

import java.util.Optional;


public abstract class SimpleParamController<T extends MyEntity, S extends SimpleParamService> {

    @Autowired
    private S s;

    @GetMapping("/exists-by-name")
    public boolean existsByName(@RequestParam(name = "name") String name) {
        return s.existsByName(name);
    }

    @GetMapping("/find-by-name")
    public Optional<T> findByName(@RequestParam(name = "name") String name) {
        return s.findByName(name);
    }
}

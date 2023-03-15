package pl.pracinho.countrycitygame.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pracinho.countrycitygame.model.entity.parent.MyEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class City extends MyEntity {

    @Id
    @GenericGenerator(name = "cityIdGen", strategy = "increment")
    @GeneratedValue(generator = "cityIdGen")
    private Long id;

    private String uuid;

    private String name;

    public City(String name) {
        this.name = name.toUpperCase();
        this.uuid = UUID.randomUUID().toString();
    }
}
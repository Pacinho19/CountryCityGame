package pl.pracingo.countrycitygame.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Country {

    @Id
    @GenericGenerator(name = "countryIdGen", strategy = "increment")
    @GeneratedValue(generator = "countryIdGen")
    private Long id;

    private String uuid;

    private String name;

    public Country(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }
}
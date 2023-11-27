package kz.timka.tacocloud.data;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Ingredient {
    //The Persistable interface provides a simple option to customize the state detection algorithm used for a specific entity class. It defines the isNew() method, which Spring Data JPA calls to determine the state of an entity object. By implementing that method, you can adjust the detection algorithm to the specific needs of your domain model.
    //  Интерфейс Persistable был нужен при работе с Spring Data JDBC, только чтобы определить, должен ли создаваться новый или обновляться существующий объ- ект; JPA определяет это автоматически.
    @Id
    private String id;
    private String name;
    private Type type;


    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}

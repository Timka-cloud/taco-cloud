package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {


}

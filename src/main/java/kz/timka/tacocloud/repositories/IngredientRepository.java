package kz.timka.tacocloud.repositories;

import kz.timka.tacocloud.data.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {


}

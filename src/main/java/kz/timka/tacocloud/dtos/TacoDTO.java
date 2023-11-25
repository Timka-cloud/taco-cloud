package kz.timka.tacocloud.dtos;

import kz.timka.tacocloud.data.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class TacoDTO {
    private String name;
    private List<String> ingredients;
}

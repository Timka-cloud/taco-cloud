package kz.timka.tacocloud.controllers;

import kz.timka.tacocloud.config.DesignProps;
import kz.timka.tacocloud.converters.IngredientByIdConverter2;
import kz.timka.tacocloud.data.Ingredient;
import kz.timka.tacocloud.data.Taco;
import kz.timka.tacocloud.data.TacoOrder;
import kz.timka.tacocloud.dtos.TacoDTO;
import kz.timka.tacocloud.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final DesignProps designProps;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, DesignProps designProps) {
        this.ingredientRepository = ingredientRepository;
        this.designProps = designProps;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType((List<Ingredient>) ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .limit(designProps.getQuantity())
                .collect(Collectors.toList());
    }

    @PostMapping // with auto converter
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder tacoOrder)
    {
        if(errors.hasErrors()) {
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
        /*
        Аннотация @ModelAttribute перед параметром TacoOrder указывает, что он дол- жен использовать объект TacoOrder, который был помещен в модель методом order() с аннотацией @ModelAttribute
         */
    }

//    @PostMapping // without auto converter
//    public String processTaco(TacoDTO tacoDTO,
//                              @ModelAttribute TacoOrder tacoOrder) {
//        IngredientByIdConverter2 converter2 = new IngredientByIdConverter2();
//        List<Ingredient> list = new ArrayList<>();
//        Taco taco = new Taco();
//        taco.setName(tacoDTO.getName());
//        for (int i = 0; i < tacoDTO.getIngredients().size(); i++) {
//            list.add(converter2.convert(tacoDTO.getIngredients().get(i)));
//        }
//        taco.setIngredients(list);
//        tacoOrder.addTaco(taco);
//        log.info("Processing taco: {}", tacoDTO);
//        return "redirect:/orders/current";
//        /*
//        Аннотация @ModelAttribute перед параметром TacoOrder указывает, что он дол- жен использовать объект TacoOrder, который был помещен в модель методом order() с аннотацией @ModelAttribute
//         */
//    }

}

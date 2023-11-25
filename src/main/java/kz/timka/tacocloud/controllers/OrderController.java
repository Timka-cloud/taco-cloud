package kz.timka.tacocloud.controllers;

import kz.timka.tacocloud.data.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus)
    {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        /*
        метод pro- cessOrder() вызывает метод setComplete() объекта SessionStatus, пе- реданного в параметре. Первоначально объект TacoOrder был создан и помещен в сеанс, когда пользователь создал свой первый тако. Вы- зывая setComplete(), мы гарантируем, что сеанс будет очищен и готов к приему нового заказа, когда пользователь создаст тако.
         */
        return "redirect:/";
    }

}

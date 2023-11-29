package kz.timka.tacocloud.controllers;

import kz.timka.tacocloud.config.OrderProps;
import kz.timka.tacocloud.data.TacoOrder;
import kz.timka.tacocloud.data.User;
import kz.timka.tacocloud.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderProps props;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderProps props, OrderRepository orderRepository) {
        this.props = props;
        this.orderRepository = orderRepository;
    }



    @GetMapping("/current")
    public String orderForm(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               Authentication authentication)
    {
        if(errors.hasErrors()) {
            return "orderForm";
        }
        order.setPlacedAt(new Date());
        User user = (User) authentication.getPrincipal();
        order.setUser(user);
        orderRepository.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        /*
        метод pro- cessOrder() вызывает метод setComplete() объекта SessionStatus, пе- реданного в параметре. Первоначально объект TacoOrder был создан и помещен в сеанс, когда пользователь создал свой первый тако. Вы- зывая setComplete(), мы гарантируем, что сеанс будет очищен и готов к приему нового заказа, когда пользователь создаст тако.
         */
        return "redirect:/";
    }

    /*
    У нас есть несколько способов определить пользователя. Вот неко-торые наиболее распространенные:
 внедрить объект java.security.Principal в метод контроллера;
 внедрить объект org.springframework.security.core.Authentication в метод контроллера;
 использовать org.springframework.security.core.context.SecurityContextHolder, чтобы получить контекст безопасности;
внедрить параметр метода с аннотацией @AuthenticationPrincipal. (Аннотация @AuthenticationPrincipal реализована в пакете
org.springframework.security.core.annotation.)
     */

    @GetMapping
    public String orderForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

}

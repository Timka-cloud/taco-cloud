package kz.timka.tacocloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }

    /*
    Первое, что следует отметить в классе WebConfig, – он реализует интерфейс WebMvcConfigurer.
    Этот интерфейс определяет несколько методов настройки Spring MVC.
    Несмотря на то что это интерфейс, он предоставляет реализации по умолчанию для всех методов,
    поэтому нам остается переопределить только те методы, которые нам нужны.
    В этом случае мы переопределили addViewControllers().

    Метод addViewControllers() получает экземпляр ViewController- Registry,
    с помощью которого можно зарегистрировать один или не- сколько контроллеров представлений.
    Здесь мы вызываем addView- Controller() с аргументом "/", определяющим путь в запросах GET,
    которые должен обрабатывать этот контроллер представления.
    Этот метод возвращает объект ViewControllerRegistration,
    для которого мы тут же вызываем setViewName(), чтобы указать имя home представ- ления,
    которому должны передаваться запросы "/".

    Контроллер HomeController можно также заменить несколькими строками в классе конфигурации,
    после чего удалить HomeController, и приложение будет действовать так же, как раньше.
    Единственное, что при этом потребуется изменить, – вернуться к классу HomeCont- rollerTest,
    представленному в главе 1, и удалить ссылку на HomeCont- roller из аннотации @WebMvcTest,
    чтобы тестовый класс компилиро- вался без ошибок.
     */
}

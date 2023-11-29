package kz.timka.tacocloud.config;

import kz.timka.tacocloud.data.Ingredient;
import kz.timka.tacocloud.repositories.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
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

    // Предварительная загрузка данных с помощью CommandLineRunner
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

            }
        };
    }
    /*
    При работе с JdbcTemplate мы организовали предварительную за- грузку объектов Ingredient на запуске приложения, создав файл data. sql, который использовался для заполнения базы данных в момент создания bean-компонента, представляющего источник данных. Тот же подход можно использовать с Spring Data JDBC. На самом деле он будет работать с любым механизмом хранения данных, основанным на реляционной базе данных. Но давайте рассмотрим другой способ предварительного заполнения базы данных, который предлагает не- много больше гибкости.
     Удобство применения интерфейса CommandLineRunner или Appli- cationRunner для первоначальной загрузки данных состоит в том, что в этом случае вместо SQL-сценария используются репозитории, то есть они одинаково хорошо будут работать и с реляционными, и с нереляционными базами данных.
     */

}

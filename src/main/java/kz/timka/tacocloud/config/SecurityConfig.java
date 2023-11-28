package kz.timka.tacocloud.config;

import kz.timka.tacocloud.data.User;
import kz.timka.tacocloud.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { // change method name for testing
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username);
                if (user != null) return user; // дальше уже под капотом проверет совпадение пароля и так далее
                throw new UsernameNotFoundException("User: '" + username + "' not found");
            }
        };

        //Поскольку нам нужно, только чтобы наша реализация UserDetailsService делегировала выполнение операций репозиторию UserReposi- tory, ее можно объявить как bean-компонент, используя следующий метод
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/design", "/orders").hasRole("USER")
                .antMatchers("/","/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate") // после логина запрос будет отправлен на /authenticate
                .usernameParameter("user").passwordParameter("pwd") // и жду два поле user и pwd, в login.html указал там
                .and()
                .csrf().disable()
                .headers().frameOptions().disable() // чтоб h2-console мог открывать
                .and()
                .build();
    }

//    @Bean  // Служба хранения сведений о пользователях в памяти
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//        // let's add two user
//        usersList.add(new User("buzz", // first user
//                encoder.encode("100"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//
//        usersList.add(new User("woody", // second user
//                encoder.encode("100"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//
//        return new InMemoryUserDetailsManager(usersList); // Служба хранения сведений о пользователях в памяти
//
//        //Единственное место, где эта служба может хранить информацию об учетных записях пользователей, – это память. Предположим, у нас всего несколько учетных записей, которые вряд ли изменятся в буbдущем. В этом случае было бы более чем достаточно определить эти учетные записи как часть конфигурации безопасности.
//
//    }

//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            return http
//                .authorizeRequests()
//                .antMatchers("/design", "/orders").access("hasRole(‘USER’) && " +
//                        "T(java.util.Calendar).getInstance().get("+
//                        "T(java.util.Calendar).DAY_OF_WEEK) == " +
//                        "T(java.util.Calendar).TUESDAY")
//                .antMatchers("/", "/**").access("permitAll")
//                .and()
//                .build();

    //разрешить создавать новые тако только пользователям с полномочиями ROLE_USER и только по вторникам.
    //Язык SpEL обеспечивает практически неограниченные возможности настройки системы безопасности. Могу поспорить, что вы уже на- чали придумывать свои интересные правила на основе SpEL.
    //Потребности приложения Taco Cloud в авторизации легко можно удовлетворить с помощью access() и простых выражений на SpEL.
//        }

}

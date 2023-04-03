package pl.klusek.michal.bookRental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    // deklaracja pola do przechowywania ścieżki do pliku z logami
    private final String pathToLogsFile = "logsFromAuth.txt";

    // metoda do zapisywania logów zalogowanych użytkowników
    public void logLoggedInUser(UserDetails userDetails) {
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append("User logged in; ")
                .append(userDetails.getUsername())
                .append("; ")
                .append(localDateTime)
                .append(System.lineSeparator());
        String log = sb.toString();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToLogsFile, true));
            writer.append(log);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager get() {
        UserDetails user = User.withUsername("user")
                .password(getBCryptPasswordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(getBCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .defaultSuccessUrl("/api/books")
                        .successHandler((request, response, authentication) -> {
                            // po udanym logowaniu wywołujemy metodę logLoggedInUser, aby zapisać log użytkownika do pliku
                            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                            logLoggedInUser(userDetails);
                        })
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}

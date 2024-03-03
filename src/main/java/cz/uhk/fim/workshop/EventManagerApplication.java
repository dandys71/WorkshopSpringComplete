package cz.uhk.fim.workshop;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}

package de.fhm.akfo.shop.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Initializing the SpringBoot-Application.
 *
 * @author Maximilian.Auch
 */
@SpringBootApplication
@ComponentScan({"de.fhm.akfo.shop.authentication.rest", "de.fhm.akfo.shop.authentication.service.impl"})
@EntityScan("de.fhm.akfo.shop.authentication.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

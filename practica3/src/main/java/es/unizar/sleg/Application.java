package es.unizar.sleg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.logging.Logger;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    private static Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false).run(args);
        logger.info("It works!");
    }
}

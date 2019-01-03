package pttk;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    static public Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        log.info("Application start");
        SpringApplication.run(Application.class, args);
    }
}
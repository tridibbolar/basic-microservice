package pepsico.entry.servers.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pepsico.entry.servers.registration.RegistrationServer;

import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pepsico.entry.domain.promotion")
@EntityScan("pepsico.entry.domain.promotion")
@EnableJpaRepositories("pepsico.entry.domain.promotion")
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableCaching
public class PromotionServer {

    protected Logger logger = Logger.getLogger(PromotionServer.class.getName());

    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for promotion-server.properties or
        // promotion-server.yml
        System.setProperty("spring.config.name", "promotion-server");

        SpringApplication.run(PromotionServer.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "Eureka Promotion Client application";
    }
}

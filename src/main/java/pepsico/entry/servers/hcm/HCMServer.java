package pepsico.entry.servers.hcm;

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
import pepsico.entry.domain.hcm.HCMConfiguration;
import pepsico.entry.domain.hcm.HCMRepository;
import pepsico.entry.servers.registration.RegistrationServer;

import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pepsico.entry.domain.hcm")
@EntityScan("pepsico.entry.domain.hcm")
@EnableJpaRepositories("pepsico.entry.domain.hcm")
@EnableEurekaClient
@RestController
@Import(HCMConfiguration.class)
@EnableHystrix
@EnableCaching
public class HCMServer {
    @Autowired
    protected HCMRepository hcmRepository;

    protected Logger logger = Logger.getLogger(HCMServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for hcms-server.properties or
        // hcms-server.yml
        System.setProperty("spring.config.name", "hcm-server");

        SpringApplication.run(HCMServer.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "Eureka HCM Client application";
    }
}

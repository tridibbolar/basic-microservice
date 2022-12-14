package pepsico.entry.servers.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pepsico.entry.domain.employee.EmployeeConfiguration;
import pepsico.entry.domain.employee.EmployeeRepository;
import pepsico.entry.servers.registration.RegistrationServer;

import java.util.logging.Logger;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pepsico.entry.domain.employee")
@EntityScan("pepsico.entry.domain.employee")
@EnableJpaRepositories("pepsico.entry.domain.employee")
@EnableEurekaClient
@RestController
@Import(EmployeeConfiguration.class)
public class EmployeeServer {

    @Autowired
    protected EmployeeRepository employeeRepository;

    protected Logger logger = Logger.getLogger(EmployeeServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for employees-server.properties or
        // employees-server.yml
        System.setProperty("spring.config.name", "employee-server");

        SpringApplication.run(EmployeeServer.class, args);
    }

    @RequestMapping(value = "/")
    public String home() {
        return "Eureka EMPLOYEE Client application";
    }
}

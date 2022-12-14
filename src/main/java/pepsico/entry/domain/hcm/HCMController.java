package pepsico.entry.domain.hcm;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pepsico.entry.domain.employee.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@RestController
public class HCMController {
    @Autowired
    RestTemplate restTemplate;
    protected Logger logger = Logger.getLogger(HCMController.class
            .getName());

    //@Autowired
    private HCMService service;
    protected HCMRepository hcmRepository;
    private CacheManager cacheManager;

    @Autowired
    public HCMController(HCMRepository hcmRepository, CacheManager cacheManager) {
        this.hcmRepository = hcmRepository;
        this.cacheManager = cacheManager;

        logger.info("HCMRepository says system has "
                + hcmRepository.countHcm() + " entries");
        service = new HCMService(hcmRepository);
    }

    @GetMapping("/hcm/role/{role}")
    public List<HCM> byRole(@PathVariable("role") String role) throws TimeoutException {
            return service.byRole(role);
    }

    @GetMapping("/hcm/{employeeIdList}")
    public List<HCM> byEmployeeId(@PathVariable("employeeIdList") List<Long> employeeIdList) throws TimeoutException {
        return service.byEmployeeId(employeeIdList);
    }

    @GetMapping("/hcm/employee/{name}")
    @HystrixCommand(fallbackMethod = "fallbackFromCache")
    @CachePut(value = "employees", key = "#root.methodName")
    public List<Employee> getEmployees(@PathVariable("name") String name)
    {
        ResponseEntity<List<Employee>> response = restTemplate.exchange("http://employee-service/employees/partname/{name}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {}, name);
        return response.getBody();
    }

    /*@GetMapping("/hcm/{id}")
    public HCM byId(@PathVariable("id") Long id) {
        return service.byId(id);
    }*/

    @PostMapping("/hcm")
    public ResponseEntity<HCM> createHcm(@RequestBody HCM hcm) {
        return service.create(hcm);
    }

    @PutMapping("/hcm/{id}")
    public ResponseEntity<HCM> updateHcm(@PathVariable("id") long id, @RequestBody HCM hcm) {
        return service.update(id,hcm);
    }

    @DeleteMapping("/hcm/{id}")
    public ResponseEntity<HCM> deleteHcm(@PathVariable("id") long id) {
        return service.delete(id);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private List<Employee> fallbackFromCache(String name, Throwable throwable){
        logger.info("Fetching from employees cache...");
        return (List<Employee> )cacheManager.getCache("employees").get("getEmployees").get();
    }

    /*
    * Hystric check
    *
    @GetMapping("/hcm/hys")
    @HystrixCommand(fallbackMethod = "fallback_hello", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String hello() throws InterruptedException {
        Thread.sleep(1100);
        return "Welcome Hystrix";
    }
    private String fallback_hello(){
        return "Request fails. It takes long time to response";
    }
    */

}

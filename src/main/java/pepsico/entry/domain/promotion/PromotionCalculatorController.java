package pepsico.entry.domain.promotion;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pepsico.entry.domain.employee.Employee;
import pepsico.entry.domain.employee.EmployeeService;
import pepsico.entry.domain.hcm.HCM;
import pepsico.entry.domain.hcm.HCMController;
import pepsico.entry.domain.hcm.HCMRepository;
import pepsico.entry.domain.hcm.HCMService;

import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@RestController
public class PromotionCalculatorController {

    protected Logger logger = Logger.getLogger(PromotionCalculatorController.class
            .getName());

    @Autowired
    RestTemplate restTemplate;
    private PromotionCalculatorService service;

    @Autowired
    public PromotionCalculatorController() {
        service = new PromotionCalculatorService();
    }
    //@HystrixCommand(fallbackMethod = "fallbackFromCache")
    //@CachePut(value = "employees", key = "#root.methodName")
    @GetMapping("/promo/list")
    public List<Employee> isEligibleForPromotion() throws TimeoutException {
        return service.isEligibleForPromotion(restTemplate);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

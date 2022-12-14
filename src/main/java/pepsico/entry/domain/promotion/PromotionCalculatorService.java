package pepsico.entry.domain.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pepsico.entry.domain.PepsicoService;
import pepsico.entry.domain.employee.Employee;
import pepsico.entry.domain.exception.EmployeeNotFoundException;
import pepsico.entry.domain.exception.HCMNotFoundException;
import pepsico.entry.domain.hcm.HCM;
import pepsico.entry.domain.hcm.HCMRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PromotionCalculatorService {

    private RestTemplate restTemplate;
    @Autowired
    public PromotionCalculatorService() {
    }
    public List<Employee> isEligibleForPromotion(RestTemplate restTemplate) {

        ResponseEntity<List<Employee>> employeeList = restTemplate.exchange("http://employee-service/employees",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {});
        List<Long> employeeIdList = employeeList.getBody().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());

        ResponseEntity<List<HCM>> hcmList = restTemplate.exchange("http://hcm-service/hcm/{employeeIdList}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<HCM>>() {},employeeIdList.toString().replace("[", "").replace("]", "").replace(" ", ""));

        List<HCM> hcms = hcmList.getBody();
        //Rule1: 3 years in current role and if GoalCompletedForCurrentYear?
        //Rule2: 3 years in current role and if ClientAppreciationForCurrentYear?
        /**Note: This rule set is hard to expand, for full advance rule set we
        can use open source drools rule engine**/
        List<HCM> hcmEligibleForPromotion = hcms.stream()
                .filter(h -> h.getYearsInCurrentRole() >= 3
                        && (h.getGoalCompletedForCurrentYear()
                        || h.getClientAppreciationForCurrentYear()))
                .collect(Collectors.toList());

        List<Long> promoEligibleEmpIdList = hcmEligibleForPromotion.stream()
                .map(HCM::getEmployeeId)
                .collect(Collectors.toList());

        List<Employee> promoEligibleEmployees = employeeList.getBody().stream()
                .filter(e -> promoEligibleEmpIdList.contains(e.getEmployeeId()))
                .collect(Collectors.toList());

        if (promoEligibleEmployees == null || promoEligibleEmployees.size() == 0)
            throw new EmployeeNotFoundException();
            //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
        else {
            return promoEligibleEmployees;
        }
    }
}

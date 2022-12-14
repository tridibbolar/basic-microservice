package pepsico.entry.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class EmployeeController {
    protected Logger logger = Logger.getLogger(EmployeeController.class
            .getName());
    protected EmployeeRepository employeeRepository;
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

        logger.info("EmployeeRepository says system has "
                + employeeRepository.countEmployees() + " employees");
        service = new EmployeeService(employeeRepository);
    }

    @GetMapping("/employees/{id}")
    public Employee byId(@PathVariable("id") Long id) {
        return service.byId(id);
    }

    @GetMapping("/employees")
    public List<Employee> readAllActive() {
        return service.readAllActive();
    }

    @GetMapping("/employees/name/{name}")
    public Employee byName(@PathVariable("name") String name) {
        return service.byName(name);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createHcm(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateHcm(@PathVariable("id") long id, @RequestBody Employee employee) {
        return service.update(id,employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Employee> deleteHcm(@PathVariable("id") long id) {
        return service.delete(id);
    }

    @RequestMapping("/employees/partname/{name}")
    public List<Employee> byPartialName(@PathVariable("name") String partialName) {
        return service.byPartialName(partialName);
    }
}

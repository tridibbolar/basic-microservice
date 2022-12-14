package pepsico.entry.domain.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import pepsico.entry.domain.PepsicoService;
import pepsico.entry.domain.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeService implements PepsicoService<Employee> {

    protected EmployeeRepository employeeRepository;
    protected Logger logger = Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee byId(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee == null || employee.isPresent() == false)
            throw new EmployeeNotFoundException(""+id);
        else {
            return employee.get();
        }
    }

    public List<Employee> readAllActive() {
        List<Employee> employee = employeeRepository.findActiveEmployee();
        if (employee == null || employee.size() == 0)
            throw new EmployeeNotFoundException();
        else {
            return employee;
        }
    }

    @Override
    public ResponseEntity<Employee> create(Employee employee) {
        try {
            Employee _employee = new Employee();
            _employee.setEmployeeId(employee.getEmployeeId());
            _employee.setAge(employee.getAge());
            _employee.setAddress(employee.getAddress());
            _employee.setName(employee.getName());
            _employee.setGender(employee.getGender());
            _employee.setIsActive(employee.getIsActive());
            employeeRepository.save(_employee);
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Employee> update(long id, Employee employee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        if (employeeData.isPresent()) {
            Employee _employee = employeeData.get();
            _employee.setName(employee.getName());
            _employee.setAge(employee.getAge());
            _employee.setAddress(employee.getAddress());
            _employee.setGender(employee.getGender());
            _employee.setIsActive(employee.getIsActive());
            return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Employee> delete(long id) {
        Optional<Employee> employeeData = employeeRepository.findById(id);
        if (employeeData.isPresent()) {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Employee byName(String name) {
        logger.info("employees-service byName() invoked: " + name);
        Employee employee = employeeRepository.findByName(name);
        logger.info("employees-service byName() found: " + name);

        if (employee == null)
            throw new EmployeeNotFoundException(name);
        else {
            return employee;
        }
    }

    public List<Employee> byPartialName(String partialName) {
        logger.info("employees-service byName() invoked: "
                + employeeRepository.getClass().getName() + " for "
                + partialName);

        List<Employee> employees = employeeRepository
                .findByNameContainingIgnoreCase(partialName);
        logger.info("employees-service byPartialName() found: " + employees);

        if (employees == null || employees.size() == 0)
            throw new EmployeeNotFoundException(partialName);
        else {
            return employees;
        }
    }
}

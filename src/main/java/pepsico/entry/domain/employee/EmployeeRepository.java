package pepsico.entry.domain.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Employee findByName(String name);
    public List<Employee> findByNameContainingIgnoreCase(String partialName);

    @Query("SELECT e FROM Employee e WHERE e.isActive = true")
    public List<Employee> findActiveEmployee();

    @Query("SELECT count(*) from Employee")
    public int countEmployees();
}

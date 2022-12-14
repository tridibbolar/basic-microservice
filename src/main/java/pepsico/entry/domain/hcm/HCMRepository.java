package pepsico.entry.domain.hcm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pepsico.entry.domain.employee.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface HCMRepository extends JpaRepository<HCM, Long> {
    public List<HCM> findByCurrentRole(String role);
    public List<HCM> findByExperience(BigDecimal exp);

    public List<HCM> findByGoalCompletedForCurrentYear(Boolean isGoalComp);
    public List<HCM> findByClientAppreciationForCurrentYear(Boolean isClientAppreciation);

    @Query("SELECT h from HCM h WHERE h.employeeId IN :employeeIdList")
    public List<HCM> byEmployeeId(@Param("employeeIdList")  List<Long> employeeIdList);

    @Query("SELECT count(*) from HCM")
    public int countHcm();
}

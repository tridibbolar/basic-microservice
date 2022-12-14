package pepsico.entry.domain.hcm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_HCM")
public class HCM implements Serializable {
    private static final long serialVersionUID = 1L;
    public static Long nextId = 0L;
    @Id
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    protected Long employeeId;

    @Column (name="EXP")
    protected BigDecimal experience;

    @Column (name = "CURR_ROLE")
    protected String currentRole;
    @Column (name = "YRS_CURR_ROLE")
    protected Integer yearsInCurrentRole;

    @Column (name = "GOAL_COM_CURR_YR")
    protected Boolean goalCompletedForCurrentYear;

    @Column (name = "CLNT_APRCTN_CURR_YR")
    protected Boolean clientAppreciationForCurrentYear;

    @Override
    public String toString() {
        return employeeId + " [" + experience + ":" + yearsInCurrentRole + ":" + goalCompletedForCurrentYear + ":" + clientAppreciationForCurrentYear + "]";
    }
}

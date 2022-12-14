package pepsico.entry.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_EMPLOYEE")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    public static Long nextId = 0L;
    @Id
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long employeeId;

    @Column (name="name")
    protected String name;

    @Column (name = "age")
    protected Integer age;

    @Column (name = "gender")
    protected String gender;

    @Column (name = "address")
    protected String address;

    @Column (name = "isactive")
    protected Boolean isActive;

    @Override
    public String toString() {
        return name + " [" + age + ":" + gender + ":" + isActive + "]";
    }

}
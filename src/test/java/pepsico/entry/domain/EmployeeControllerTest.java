package pepsico.entry.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pepsico.entry.domain.employee.Employee;

public class EmployeeControllerTest extends AbstractTest{

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }
    @Test
    public void byName() throws Exception {
        String uri = "/employees/name/Employee One";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Employee employee = super.mapFromJson(content, Employee.class);
        Assert.assertTrue(employee.getName().equalsIgnoreCase("Employee One"));
    }

    @Test
    public void create() throws Exception {
        String uri = "/employees";
        Employee employee = new Employee();
        employee.setEmployeeId(3l);
        employee.setIsActive(true);
        employee.setName("Test Employee");
        employee.setGender("M");
        employee.setAge(44);
        employee.setAddress("test address");

        String inputJson = super.mapToJson(employee);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "{\"employeeId\":3,\"name\":\"Test Employee\",\"age\":44,\"gender\":\"M\",\"address\":\"test address\",\"isActive\":true}");
    }
}

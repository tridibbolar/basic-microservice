package pepsico.entry.domain;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pepsico.entry.domain.employee.Employee;
import pepsico.entry.domain.hcm.HCM;
import pepsico.entry.domain.promotion.PromotionCalculatorService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PromotionCalculatorServiceTest {

    private List<HCM> hcmInputList;
    private List<Employee> employeeInputList;
    private List<Employee> promotedEmployeeList;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PromotionCalculatorService underTest;

    private final Gson gson = new Gson();

    @Before
    public void setUp() throws IOException, URISyntaxException {
        hcmInputList = gson.fromJson(getJsonReaderForFile("/hcms_input.json"),
                new TypeToken<List<HCM>>() {}.getType());
        employeeInputList = gson.fromJson(getJsonReaderForFile("/employees_input.json"),
                new TypeToken<List<Employee>>() {}.getType());
    }
    @Test
    public void correctPromotionEligibilityTest() throws URISyntaxException, IOException {
        promotedEmployeeList = gson.fromJson(getJsonReaderForFile("/promotion_output.json"),
                new TypeToken<List<Employee>>() {}.getType());
        Mockito.when(restTemplate.exchange(
                Mockito.endsWith("employee-service/employees"),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(),
                Mockito.<ParameterizedTypeReference<List<Employee>>>any())
        ).thenReturn(ResponseEntity.ok(employeeInputList));

        Mockito.when(restTemplate.exchange(
                Mockito.endsWith("hcm-service/hcm/{employeeIdList}"),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(),
                Mockito.<ParameterizedTypeReference<List<HCM>>>any(),
                Mockito.any(String.class))
        ).thenReturn(ResponseEntity.ok(hcmInputList));


        List<Employee> res = underTest.isEligibleForPromotion(restTemplate);
        assertTrue(promotedEmployeeList.size() == res.size());
        assertTrue(promotedEmployeeList.get(0).getName().equalsIgnoreCase(res.get(0).getName()));
    }

    @Test
    public void wrongPromotionEligibilityTest() throws URISyntaxException, IOException {
        promotedEmployeeList = gson.fromJson(getJsonReaderForFile("/wrong_promotion_output.json"),
                new TypeToken<List<Employee>>() {}.getType());
        Mockito.when(restTemplate.exchange(
                Mockito.endsWith("employee-service/employees"),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(),
                Mockito.<ParameterizedTypeReference<List<Employee>>>any())
        ).thenReturn(ResponseEntity.ok(employeeInputList));

        Mockito.when(restTemplate.exchange(
                Mockito.endsWith("hcm-service/hcm/{employeeIdList}"),
                Mockito.eq(HttpMethod.GET),
                Mockito.any(),
                Mockito.<ParameterizedTypeReference<List<HCM>>>any(),
                Mockito.any(String.class))
        ).thenReturn(ResponseEntity.ok(hcmInputList));


        List<Employee> res = underTest.isEligibleForPromotion(restTemplate);
        assertFalse(promotedEmployeeList.size() == res.size());
        assertTrue(promotedEmployeeList.get(0).getName().equalsIgnoreCase(res.get(0).getName()));
    }

    private JsonReader getJsonReaderForFile(String filename) throws URISyntaxException, IOException {
        URL url = PromotionCalculatorServiceTest.class.getResource(filename);
        JsonReader reader = new JsonReader(Files.newBufferedReader(Paths.get(url.toURI())));
        return reader;
    }
}

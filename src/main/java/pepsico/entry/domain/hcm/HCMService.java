package pepsico.entry.domain.hcm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pepsico.entry.domain.PepsicoService;
import pepsico.entry.domain.employee.Employee;
import pepsico.entry.domain.exception.HCMNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class HCMService implements PepsicoService<HCM> {

    protected HCMRepository hcmRepository;
    protected Logger logger = Logger.getLogger(HCMService.class.getName());

    @Autowired
    public HCMService(HCMRepository hcmRepository) {
        this.hcmRepository = hcmRepository;
    }
    public List<HCM> byRole(String role) {
        logger.info("hcm-service byRole() invoked: " + role);
        List<HCM> hcms = hcmRepository.findByCurrentRole(role);
        logger.info("hcm-service byRole() found: " + role);

        if (hcms == null || hcms.size() == 0)
            throw new HCMNotFoundException(role);
            //throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
        else {
            return hcms;
        }
    }

    public List<HCM> byEmployeeId(List<Long> employeeIdList) {
        List<HCM> hcms = hcmRepository.byEmployeeId(employeeIdList);
        if (hcms == null || hcms.size() == 0)
            throw new HCMNotFoundException();
        else {
            return hcms;
        }
    }
    @Override
    public HCM byId(Long id) {
        logger.info("hcm-service byId() invoked: " + id);
        Optional<HCM> hcm = hcmRepository.findById(id);
        logger.info("hcm-service byId() found: " + id);

        if (hcm == null || hcm.isPresent() == false)
            throw new HCMNotFoundException(""+id);
        else {
            return hcm.get();
        }
    }

    @Override
    public ResponseEntity<HCM> create(HCM hcm) {
        try {
            HCM _hcm = new HCM();
            _hcm.setEmployeeId(hcm.getEmployeeId());
            _hcm.setExperience(hcm.getExperience());
            _hcm.setCurrentRole(hcm.getCurrentRole());
            _hcm.setYearsInCurrentRole(hcm.getYearsInCurrentRole());
            _hcm.setGoalCompletedForCurrentYear(hcm.getGoalCompletedForCurrentYear());
            _hcm.setClientAppreciationForCurrentYear(hcm.getClientAppreciationForCurrentYear());
            hcmRepository.save(_hcm);
            return new ResponseEntity<>(_hcm, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HCM> update(long id, HCM hcm) {
        Optional<HCM> hcmData = hcmRepository.findById(id);
        if (hcmData.isPresent()) {
            HCM _hcm = hcmData.get();
            _hcm.setExperience(hcm.getExperience());
            _hcm.setCurrentRole(hcm.getCurrentRole());
            _hcm.setYearsInCurrentRole(hcm.getYearsInCurrentRole());
            _hcm.setGoalCompletedForCurrentYear(hcm.getGoalCompletedForCurrentYear());
            _hcm.setClientAppreciationForCurrentYear(hcm.getClientAppreciationForCurrentYear());
            return new ResponseEntity<>(hcmRepository.save(_hcm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<HCM> delete(long id) {
        Optional<HCM> hcmData = hcmRepository.findById(id);
        if (hcmData.isPresent()) {
            hcmRepository.deleteById(id);
            return new ResponseEntity<>(hcmData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

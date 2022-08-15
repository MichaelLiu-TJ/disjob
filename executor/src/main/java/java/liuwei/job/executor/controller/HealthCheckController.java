package java.liuwei.job.executor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/disjob/client")
public class HealthCheckController {

    @GetMapping(value = {"healthCheck", "health-check.html"})
    public ResponseEntity healthCheck() {
        return new ResponseEntity("<h1>LW dis job client running</>", HttpStatus.OK);
    }

}

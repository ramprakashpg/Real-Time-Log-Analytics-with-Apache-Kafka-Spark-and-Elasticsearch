package com.bd.logkafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/v1")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
}

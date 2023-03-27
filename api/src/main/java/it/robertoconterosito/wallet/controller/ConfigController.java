package it.robertoconterosito.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class ConfigController {

    @GetMapping("/health")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("OK");
    }
}

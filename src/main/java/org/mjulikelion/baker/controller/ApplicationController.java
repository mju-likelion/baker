package org.mjulikelion.baker.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.mjulikelion.baker.model.Application;
import org.mjulikelion.baker.repository.ApplicationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("application")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;

    @GetMapping("/test")
    public List<Application> test() {
        return this.applicationRepository.findAll();
    }
}

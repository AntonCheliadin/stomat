package com.stomat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {

    @GetMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
    }
}

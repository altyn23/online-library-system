package com.example.onlinelibrarysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @GetMapping("/books")
    public List<String> getBooks() {
        return List.of(
                "Clean Code",
                "Java Fundamentals",
                "Spring Boot in Action"
        );
    }
}
package com.example.onlinelibrarysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BookController {

    private static final List<String> BOOKS = List.of(
            "Clean Code",
            "Java Fundamentals",
            "Spring Boot in Action");

    // ТАЛАП: дәл осы endpoint list string қайтарсын
    // Қосымша фишка: ?sort=az немесе ?sort=za
    @GetMapping("/books")
    public List<String> getBooks(@RequestParam(name = "sort", required = false) String sort) {
        List<String> result = new ArrayList<>(BOOKS);

        if (sort == null)
            return result;

        String s = sort.trim().toLowerCase();
        if (s.equals("az")) {
            result.sort(String::compareToIgnoreCase);
        } else if (s.equals("za")) {
            result.sort((a, b) -> b.compareToIgnoreCase(a));
        }
        return result;
    }

    // Қосымша “қызық”: кездейсоқ ұсыныс
    @GetMapping("/books/recommend")
    public Map<String, String> recommend() {
        String pick = BOOKS.get(new Random().nextInt(BOOKS.size()));
        return Map.of(
                "suggestion", pick,
                "message", "Бүгін осы кітапты оқып көр!");
    }
}
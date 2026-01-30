package com.example.onlinelibrarysystem.controller;

import com.example.onlinelibrarysystem.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final List<Book> BOOKS = List.of(
            new Book("b1", "Clean Code", "Robert C. Martin", 2008, "Programming"),
            new Book("b2", "Effective Java", "Joshua Bloch", 2018, "Programming"),
            new Book("b3", "Spring Boot in Action", "Craig Walls", 2016, "Backend"),
            new Book("b4", "Java Fundamentals", "Oracle", 2020, "Education"),
            new Book("b5", "Design Patterns", "GoF", 1994, "Architecture"));

    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String sort) {
        String q = (search == null) ? "" : search.trim().toLowerCase();
        String g = (genre == null) ? "" : genre.trim().toLowerCase();

        List<Book> result = BOOKS.stream()
                .filter(b -> q.isEmpty() ||
                        (b.title() + " " + b.author()).toLowerCase().contains(q))
                .filter(b -> g.isEmpty() || b.genre().toLowerCase().equals(g))
                .collect(Collectors.toCollection(ArrayList::new));

        if (sort != null) {
            String s = sort.trim().toLowerCase();
            if (s.equals("az")) {
                result.sort(Comparator.comparing(Book::title, String.CASE_INSENSITIVE_ORDER));
            } else if (s.equals("za")) {
                result.sort(Comparator.comparing(Book::title, String.CASE_INSENSITIVE_ORDER).reversed());
            } else if (s.equals("year")) {
                result.sort(Comparator.comparingInt(Book::year).reversed());
            }
        }

        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable String id) {
        return BOOKS.stream()
                .filter(b -> b.id().equalsIgnoreCase(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/recommend")
    public Map<String, Object> recommend() {
        Book pick = BOOKS.get(new Random().nextInt(BOOKS.size()));
        return Map.of(
                "book", pick,
                "message", "Бүгін осы кітапты оқып көр!");
    }

    @GetMapping("/titles")
    public List<String> titles() {
        return BOOKS.stream().map(Book::title).toList();
    }
}
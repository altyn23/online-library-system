package com.example.onlinelibrarysystem.model;

public record Book(
        String id,
        String title,
        String author,
        int year,
        String genre) {
}
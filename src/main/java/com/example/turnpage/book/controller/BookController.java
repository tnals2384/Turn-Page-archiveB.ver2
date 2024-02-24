package com.example.turnpage.book.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @PostMapping("/books")
    public String postBook(String data) {
        return "hi";
    }
}

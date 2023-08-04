package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

  private final BookService bookService;

  BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping("/add")
  public ResponseEntity<Book> add(@RequestBody Book book) {
    return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<Book> update(@RequestBody Book book) {
    return new ResponseEntity<>(bookService.updateBook(book), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Book>> get(@RequestParam String title, @RequestParam String author) {
    return new ResponseEntity<>(bookService.getBook(title, author), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestParam String isbn) {
    bookService.deleteBook(isbn);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

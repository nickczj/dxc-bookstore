package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  Book findByIsbn(String isbn);
  List<Book> findAllByTitle(String title);
  long deleteByIsbn(String isbn);
}

package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookService {

  public final BookRepository bookRepository;
  public final AuthorRepository authorRepository;

  BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public Book addBook(Book book) {
    var authors = book.getAuthors();
    var matchingAuthorsInDb = authorRepository.findAllByNameIn(authors.stream().map(Author::getName).collect(Collectors.toList()));
    var authorsToAdd = new ArrayList<Author>();
    authors.forEach(author -> {
      if (!matchingAuthorsInDb.stream().map(Author::getName).toList().contains(author.getName())) {
        authorsToAdd.add(Author.builder().name(author.getName()).birthday(author.getBirthday()).build());
      }
    });

    var authorsCombined = Stream.concat(matchingAuthorsInDb, authorsToAdd.stream().toList());
    book.setAuthors();
    book.
    return bookRepository.save(book);
  }
}

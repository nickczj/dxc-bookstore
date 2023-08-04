package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    var authorsCombined = Stream.concat(matchingAuthorsInDb.stream(), authorsToAdd.stream()).collect(Collectors.toList());
    book.setAuthors(authorsCombined);
    return bookRepository.save(book);
  }

  public Book updateBook(Book book) {
    var existingBook = bookRepository.findByIsbn(book.getIsbn());
    var authors = existingBook.getAuthors();
    var matchingAuthorsInDb = authorRepository.findAllByNameIn(authors.stream().map(Author::getName).collect(Collectors.toList()));
    var authorsToAdd = new ArrayList<Author>();
    authors.forEach(author -> {
      if (!matchingAuthorsInDb.stream().map(Author::getName).toList().contains(author.getName())) {
        authorsToAdd.add(Author.builder().name(author.getName()).birthday(author.getBirthday()).build());
      }
    });

    var authorsCombined = Stream.concat(matchingAuthorsInDb.stream(), authorsToAdd.stream()).collect(Collectors.toList());

    existingBook.setAuthors(authorsCombined);
    existingBook.setYear(book.getYear());
    existingBook.setTitle(book.getTitle());
    existingBook.setIsbn(book.getIsbn());
    existingBook.setGenre(book.getGenre());
    return bookRepository.save(existingBook);
  }

  public List<Book> getBook(String title, String author) {
    if (StringUtils.isBlank(title) && StringUtils.isBlank(author)) {
      return null;
    } else if (!StringUtils.isBlank(title) && !StringUtils.isBlank(author)) {
      return bookRepository
        .findAllByTitle(title)
        .stream()
        .filter(book -> book.getAuthors().stream().map(Author::getName).anyMatch(author::equals))
        .collect(Collectors.toList());
    } else if (!StringUtils.isBlank(author)) {
      var a = authorRepository.findByName(author);
      if (a != null) {
        return List.of(a.getBook());
      } else {
        return null;
      }
    } else {
      return bookRepository.findAllByTitle(title);
    }
  }

  // https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
  @Transactional
  public void deleteBook(String isbn) {
    bookRepository.deleteByIsbn(isbn);
  }
}

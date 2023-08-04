package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="BOOK")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name="ISBN", length=50, nullable=false, unique=true)
  private String isbn;

  @Column(name="title", length=50, nullable=false, unique=false)
  private String title;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Author> authors;

  @Column(name="year_published", nullable=false, unique=false)
  private Integer year;

  @Column(name="genre", length=50, nullable=false, unique=false)
  private String genre;
}

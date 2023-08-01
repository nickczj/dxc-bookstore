package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="AUTHOR")
@Builder
public class Author {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @Column(name="name", length=50, nullable=false, unique=false)
  private String name;

  @Column(name="birthday", nullable=false, unique=false)
  private LocalDate birthday;

  @ManyToOne
  @JoinColumn(name="ibsn", nullable = false)
  private Book book;
}

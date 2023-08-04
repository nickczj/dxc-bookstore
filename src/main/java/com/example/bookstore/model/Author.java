package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name="AUTHOR")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name="name", length=50, nullable=false, unique=false)
  private String name;

  @Column(name="birthday", nullable=false, unique=false)
  private LocalDate birthday;

  @ManyToOne
  @JoinColumn(name="isbn", nullable = false)
  @JsonIgnore
  private Book book;
}

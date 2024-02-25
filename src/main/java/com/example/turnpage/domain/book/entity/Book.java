package com.example.turnpage.domain.book.entity;

import com.example.turnpage.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE book SET deleted_at = CURRENT_TIMESTAMP WHERE book_id = ?")
@SQLRestriction("deleted_at is NULL")
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private Double star;

    private String image;

    private String description;

    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;
}

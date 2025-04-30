package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Başlığa göre kitapları bul
    List<Book> findByTitle(String title);

    // Yazara göre kitapları bul
    List<Book> findByAuthor(String author);

    // Yayın yılına göre kitapları bul
    List<Book> findByPublishedYear(int publishedYear);
}

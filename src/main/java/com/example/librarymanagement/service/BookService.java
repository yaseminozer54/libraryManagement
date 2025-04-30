package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    // Constructor injection (modern ve test edilebilir yöntem)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Tüm kitapları getir
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ID ile kitap getir
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Yeni kitap oluştur
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Kitap güncelle
    public Book updateBook(Long id, Book book) {
        return bookRepository.existsById(id) ? bookRepository.save(updateBookFields(id, book)) : null;
    }

    // Kitap sil
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Başlığa göre ara
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // Yazara göre ara
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    // Yıla göre ara
    public List<Book> getBooksByPublishedYear(int year) {
        return bookRepository.findByPublishedYear(year);
    }

    // Güncellenen kitap alanlarını ayarla
    private Book updateBookFields(Long id, Book book) {
        book.setId(id);
        return book;
    }
}

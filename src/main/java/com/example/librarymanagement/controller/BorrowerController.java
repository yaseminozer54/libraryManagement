package com.example.librarymanagement.controller;
import com.example.librarymanagement.dto.BorrowerDTO;
import com.example.librarymanagement.dto.BorrowerRequest;
import com.example.librarymanagement.model.Borrower;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.service.BorrowerService;
import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")

public class BorrowerController {
    private final BorrowerService borrowerService;
    private final MemberService memberService;
    private final BookService bookService;

    public BorrowerController(BorrowerService borrowerService, MemberService memberService, BookService bookService) {
        this.borrowerService = borrowerService;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    // Tüm Borrower kayıtlarını getir (DTO ile)
    @GetMapping
    public ResponseEntity<List<BorrowerDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    // ID'ye göre Borrower getir
    @GetMapping("/{id}")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni Borrower kaydı oluştur
    @PostMapping
    public ResponseEntity<Borrower> addBorrower(@RequestBody BorrowerRequest request) {
        Optional<Member> memberOpt = memberService.getMemberById(request.getMemberId());
        Optional<Book> bookOpt = bookService.getBookById(request.getBookId());

        if (memberOpt.isEmpty() || bookOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Borrower borrower = new Borrower();
        borrower.setMember(memberOpt.get());
        borrower.setBook(bookOpt.get());
        borrower.setBorrowDate(request.getBorrowDate());
        borrower.setReturnDate(request.getReturnDate());
        borrower.setReturned(request.isReturned());

        return ResponseEntity.ok(borrowerService.addBorrower(borrower));
    }

    // Borrower güncelle (Tarih ve iade bilgisi)
    @PutMapping("/{id}")
    public ResponseEntity<Borrower> updateBorrower(@PathVariable Long id, @RequestBody Borrower borrower) {
        if (borrower.getBorrowDate() == null || borrower.getReturnDate() == null) {
            return ResponseEntity.badRequest().build();
        }

        Borrower updated = borrowerService.updateBorrower(
                id,
                borrower.getBorrowDate(),
                borrower.getReturnDate(),
                borrower.isReturned()
        );
        return ResponseEntity.ok(updated);
    }

    // Kitabı iade olarak işaretle
    @PutMapping("/{id}/return")
    public ResponseEntity<Borrower> returnBook(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id)
                .map(borrower -> {
                    borrower.setReturned(true);
                    borrower.setReturnDate(LocalDate.now());
                    Borrower updated = borrowerService.updateBorrower(
                            id,
                            borrower.getBorrowDate(),
                            borrower.getReturnDate(),
                            true
                    );
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Borrower kaydını sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        try {
            if (borrowerService.deleteBorrower(id)) {
                return ResponseEntity.noContent().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

package com.example.librarymanagement.service;
import com.example.librarymanagement.dto.BorrowerDTO;
import com.example.librarymanagement.model.Borrower;
import com.example.librarymanagement.repository.BorrowerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {
    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    // Tüm borrower kayıtlarını DTO olarak getir
    public List<BorrowerDTO> getAllBorrowers() {
        return borrowerRepository.findAllBorrowersWithNames();
    }

    // ID ile borrower getir
    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);
    }

    // Yeni borrower ekle
    public Borrower addBorrower(Borrower borrower) {
        borrower.setReturned(false); // varsayılan: henüz iade edilmedi
        return borrowerRepository.save(borrower);
    }

    // Borrower güncelle
    public Borrower updateBorrower(Long id, LocalDate borrowDate, LocalDate returnDate, boolean isReturned) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Borrower not found with ID: " + id));

        borrower.setBorrowDate(borrowDate);
        borrower.setReturnDate(returnDate);
        borrower.setReturned(isReturned);

        return borrowerRepository.save(borrower);
    }

    // Borrower sil
    public boolean deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id)) {
            throw new IllegalArgumentException("Borrower not found with ID: " + id);
        }
        borrowerRepository.deleteById(id);
        return true;
    }
}

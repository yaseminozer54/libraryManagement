package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Borrower;
import com.example.librarymanagement.model.Member;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.dto.BorrowerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long>{
    List<Borrower> findByMember(Member member);

    List<Borrower> findByBook(Book book);

    List<Borrower> findByIsReturned(boolean isReturned);

    @Query("SELECT new com.example.librarymanagement.dto.BorrowerDTO(" +
            "b.id, b.member.name, b.book.title, b.borrowDate, b.returnDate, b.isReturned) " +
            "FROM Borrower b")
    List<BorrowerDTO> findAllBorrowersWithNames();
}

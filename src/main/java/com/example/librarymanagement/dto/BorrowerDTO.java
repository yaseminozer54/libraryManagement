package com.example.librarymanagement.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowerDTO {
    private Long id;
    private String memberName;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;

    // "Yes" veya "No" şeklinde dönüş sağlayan yardımcı metot
    public String getReturnedStatus() {
        return isReturned ? "Yes" : "No";
    }
}

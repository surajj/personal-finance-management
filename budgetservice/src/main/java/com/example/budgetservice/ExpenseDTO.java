package com.example.budgetservice;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO {
    private String description;
    private Double amount;
    private String category;
}

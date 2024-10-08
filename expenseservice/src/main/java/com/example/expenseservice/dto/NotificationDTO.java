package com.example.expenseservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String category;
    private String expenseDescription;
    private Double expenseAmount;
}

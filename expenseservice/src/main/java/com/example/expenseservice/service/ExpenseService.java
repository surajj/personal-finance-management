package com.example.expenseservice.service;

import com.example.expenseservice.dto.NotificationDTO;
import com.example.expenseservice.entity.Expense;
import com.example.expenseservice.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public Expense addExpense(Expense expense) {

        Expense newExpense =  expenseRepository.save(expense);

        // Call Notification Service
        String notificationUrl = "http://localhost:8084/notifications";
        NotificationDTO notificationRequest = new NotificationDTO(expense.getCategory(), expense.getDescription(), expense.getAmount());
        try {
            // Send the notification request
            String s = restTemplate.postForObject(notificationUrl, notificationRequest, String.class);
            System.out.println(s);
        } catch (RestClientException e) {
            // Handle error (logging, retry logic, etc.)
            e.printStackTrace();
            // Optionally, you can handle the situation, e.g., roll back the expense if needed
        }
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
}

package com.example.expenseservice.controller;

import com.example.expenseservice.entity.Expense;
import com.example.expenseservice.dto.NotificationDTO;
import com.example.expenseservice.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/expense")
public class ExpenseController {


    @Autowired
    private ExpenseService expenseService;


    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {

        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category) {
        return expenseService.getExpensesByCategory(category);
    }
}

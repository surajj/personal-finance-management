package com.example.budgetservice.service;

import com.example.budgetservice.dto.ExpenseDTO;
import com.example.budgetservice.entity.Budget;
import com.example.budgetservice.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Transactional
    public Budget createBudget(Budget budget) {

        String expenseUrl = "http://localhost:8083/expense";
        budget = budgetRepository.save(budget);

        ExpenseDTO expenseDTO =  ExpenseDTO.builder().category(budget.getCategory()).amount(budget.getAmount()).build();

        try {
            // Send the notification request
            restTemplate.postForObject(expenseUrl, expenseDTO, ExpenseDTO.class);
        } catch (RestClientException e) {
            // Handle error (logging, retry logic, etc.)
            e.printStackTrace();
            // Optionally, you can handle the situation, e.g., roll back the expense if needed
        }
        return budget;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        return budget.orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    public Budget updateBudget(Long id, Budget updatedBudget) {
        Budget budget = getBudgetById(id);
        budget.setAmount(updatedBudget.getAmount());
        budget.setRemainingAmount(updatedBudget.getRemainingAmount());
        budget.setCategory(updatedBudget.getCategory());
        return budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }

    public List<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }
}

package com.example.budgetservice.service;

import com.example.budgetservice.entity.Budget;
import com.example.budgetservice.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
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

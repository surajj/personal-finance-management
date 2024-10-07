package com.example.budgetservice.repository;

import com.example.budgetservice.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
    /**
     * Find by user id
     * @param userId user id
     * @return List of budget
     */
    List<Budget> findByUserId(Long userId);
}

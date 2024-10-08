package com.example.budgetservice;

import com.example.budgetservice.dto.ExpenseDTO;
import com.example.budgetservice.service.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.budgetservice.entity.Budget;
import com.example.budgetservice.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class BudgetserviceApplicationTests {

	@Mock
	private BudgetRepository budgetRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private BudgetService budgetService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createBudget_Success() {
		Budget budget = new Budget();
		budget.setCategory("Food");
		budget.setAmount(1000.0);

		when(budgetRepository.save(any(Budget.class))).thenReturn(budget);

		ExpenseDTO expenseDTO = ExpenseDTO.builder().category("Food").amount(1000.0).build();

		when(restTemplate.postForObject(anyString(), any(ExpenseDTO.class), eq(ExpenseDTO.class)))
				.thenReturn(expenseDTO);

		Budget createdBudget = budgetService.createBudget(budget);

		assertNotNull(createdBudget);
		assertEquals("Food", createdBudget.getCategory());
		assertEquals(1000.0, createdBudget.getAmount());

		verify(budgetRepository, times(1)).save(budget);
		verify(restTemplate, times(1)).postForObject(anyString(), eq(expenseDTO), eq(ExpenseDTO.class));
	}


	@Test
	void createBudget_RestTemplateThrowsException() {
		Budget budget = new Budget();
		budget.setCategory("Food");
		budget.setAmount(1000.0);

		when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
		doThrow(new RestClientException("Service Unavailable"))
				.when(restTemplate).postForObject(anyString(), any(ExpenseDTO.class), eq(ExpenseDTO.class));

		Budget createdBudget = budgetService.createBudget(budget);

		assertNotNull(createdBudget);
		assertEquals("Food", createdBudget.getCategory());
		assertEquals(1000.0, createdBudget.getAmount());

		verify(budgetRepository, times(1)).save(budget);
		verify(restTemplate, times(1)).postForObject(anyString(), any(ExpenseDTO.class), eq(ExpenseDTO.class));
	}

	@Test
	void getAllBudgets_Success() {
		Budget budget1 = new Budget();
		budget1.setCategory("Food");
		budget1.setAmount(1000.0);

		Budget budget2 = new Budget();
		budget2.setCategory("Transport");
		budget2.setAmount(500.0);

		when(budgetRepository.findAll()).thenReturn(Arrays.asList(budget1, budget2));

		List<Budget> budgets = budgetService.getAllBudgets();

		assertNotNull(budgets);
		assertEquals(2, budgets.size());
		verify(budgetRepository, times(1)).findAll();
	}

	@Test
	void getBudgetById_Success() {
		Budget budget = new Budget();
		budget.setId(1L);
		budget.setCategory("Food");
		budget.setAmount(1000.0);

		when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

		Budget foundBudget = budgetService.getBudgetById(1L);

		assertNotNull(foundBudget);
		assertEquals(1L, foundBudget.getId());
		assertEquals("Food", foundBudget.getCategory());
		verify(budgetRepository, times(1)).findById(1L);
	}

	@Test
	void getBudgetById_NotFound() {
		when(budgetRepository.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			budgetService.getBudgetById(1L);
		});

		assertEquals("Budget not found", exception.getMessage());
		verify(budgetRepository, times(1)).findById(1L);
	}

	@Test
	void updateBudget_Success() {
		Budget budget = new Budget();
		budget.setId(1L);
		budget.setCategory("Food");
		budget.setAmount(1000.0);

		Budget updatedBudget = new Budget();
		updatedBudget.setAmount(1200.0);
		updatedBudget.setCategory("Groceries");

		when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));
		when(budgetRepository.save(any(Budget.class))).thenReturn(budget);

		Budget result = budgetService.updateBudget(1L, updatedBudget);

		assertNotNull(result);
		assertEquals(1200.0, result.getAmount());
		assertEquals("Groceries", result.getCategory());

		verify(budgetRepository, times(1)).findById(1L);
		verify(budgetRepository, times(1)).save(budget);
	}

	@Test
	void deleteBudget_Success() {
		doNothing().when(budgetRepository).deleteById(1L);

		budgetService.deleteBudget(1L);

		verify(budgetRepository, times(1)).deleteById(1L);
	}

	@Test
	void getBudgetsByUserId_Success() {
		Budget budget1 = new Budget();
		budget1.setUserId(1L);
		budget1.setCategory("Food");

		Budget budget2 = new Budget();
		budget2.setUserId(1L);
		budget2.setCategory("Transport");

		when(budgetRepository.findByUserId(1L)).thenReturn(Arrays.asList(budget1, budget2));

		List<Budget> budgets = budgetService.getBudgetsByUserId(1L);

		assertNotNull(budgets);
		assertEquals(2, budgets.size());
		verify(budgetRepository, times(1)).findByUserId(1L);
	}
}

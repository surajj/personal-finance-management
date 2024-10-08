package com.example.expenseservice;

import com.example.expenseservice.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.expenseservice.dto.NotificationDTO;
import com.example.expenseservice.entity.Expense;
import com.example.expenseservice.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExpenseserviceApplicationTests {


	@Mock
	private ExpenseRepository expenseRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ExpenseService expenseService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void addExpense_Success() {
		Expense expense = new Expense();
		expense.setCategory("Food");
		expense.setDescription("Groceries");
		expense.setAmount(100.0);

		when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

		NotificationDTO notificationDTO = new NotificationDTO("Food", "Groceries", 100.0);
		when(restTemplate.postForObject(anyString(), any(NotificationDTO.class), eq(String.class)))
				.thenReturn("Notification Sent");

		Expense addedExpense = expenseService.addExpense(expense);

		assertNotNull(addedExpense);
		assertEquals("Food", addedExpense.getCategory());
		assertEquals("Groceries", addedExpense.getDescription());
		assertEquals(100.0, addedExpense.getAmount());

		verify(expenseRepository, times(2)).save(expense);  // One before and one after notification
		verify(restTemplate, times(1)).postForObject(anyString(), eq(notificationDTO), eq(String.class));
	}

	@Test
	void addExpense_RestTemplateThrowsException() {
		Expense expense = new Expense();
		expense.setCategory("Food");
		expense.setDescription("Groceries");
		expense.setAmount(100.0);

		when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

		NotificationDTO notificationDTO = new NotificationDTO("Food", "Groceries", 100.0);
		doThrow(new RestClientException("Service Unavailable"))
				.when(restTemplate).postForObject(anyString(), any(NotificationDTO.class), eq(String.class));

		Expense addedExpense = expenseService.addExpense(expense);

		assertNotNull(addedExpense);
		assertEquals("Food", addedExpense.getCategory());
		assertEquals("Groceries", addedExpense.getDescription());
		assertEquals(100.0, addedExpense.getAmount());

		verify(expenseRepository, times(2)).save(expense);  // One before and one after notification
		verify(restTemplate, times(1)).postForObject(anyString(), eq(notificationDTO), eq(String.class));
	}

	@Test
	void getAllExpenses_Success() {
		Expense expense1 = new Expense();
		expense1.setCategory("Food");
		expense1.setAmount(100.0);

		Expense expense2 = new Expense();
		expense2.setCategory("Transport");
		expense2.setAmount(50.0);

		when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

		List<Expense> expenses = expenseService.getAllExpenses();

		assertNotNull(expenses);
		assertEquals(2, expenses.size());
		verify(expenseRepository, times(1)).findAll();
	}

	@Test
	void getExpensesByCategory_Success() {
		Expense expense1 = new Expense();
		expense1.setCategory("Food");
		expense1.setAmount(100.0);

		Expense expense2 = new Expense();
		expense2.setCategory("Food");
		expense2.setAmount(150.0);

		when(expenseRepository.findByCategory("Food")).thenReturn(Arrays.asList(expense1, expense2));

		List<Expense> expenses = expenseService.getExpensesByCategory("Food");

		assertNotNull(expenses);
		assertEquals(2, expenses.size());
		assertEquals(100.0, expenses.get(0).getAmount());
		assertEquals(150.0, expenses.get(1).getAmount());
		verify(expenseRepository, times(1)).findByCategory("Food");
	}

}

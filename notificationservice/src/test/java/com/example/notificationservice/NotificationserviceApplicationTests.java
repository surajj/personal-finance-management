package com.example.notificationservice;

import com.example.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationserviceApplicationTests {

	@Mock
	private NotificationRepository notificationRepository;

	@InjectMocks
	private NotificationService notificationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void sendNotification_Success() {
		// Create a notification with all the fields
		Notification notification = new Notification();
		notification.setCategory("Food");
		notification.setExpenseDescription("Lunch at restaurant");
		notification.setExpenseAmount(200.0);

		// Mock the save method of notificationRepository
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

		// Call the service method
		Notification sentNotification = notificationService.sendNotification(notification);

		// Verify that the returned notification is not null and has correct fields
		assertNotNull(sentNotification);
		assertEquals("Food", sentNotification.getCategory());
		assertEquals("Lunch at restaurant", sentNotification.getExpenseDescription());
		assertEquals(200.0, sentNotification.getExpenseAmount());

		// Verify that the repository save method was called exactly once
		verify(notificationRepository, times(1)).save(notification);
	}

	@Test
	void getAllNotifications_Success() {
		// Create mock notifications
		Notification notification1 = new Notification(1L, "Transport", "Cab ride", 50.0);
		Notification notification2 = new Notification(2L, "Grocery", "Supermarket shopping", 150.0);

		// Mock the findAll method of notificationRepository
		when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

		// Call the service method
		List<Notification> notifications = notificationService.getAllNotifications();

		// Assert the size and content of the returned notifications
		assertNotNull(notifications);
		assertEquals(2, notifications.size());
		assertEquals("Transport", notifications.get(0).getCategory());
		assertEquals(50.0, notifications.get(0).getExpenseAmount());
		assertEquals("Grocery", notifications.get(1).getCategory());
		assertEquals(150.0, notifications.get(1).getExpenseAmount());

		// Verify that the repository findAll method was called exactly once
		verify(notificationRepository, times(1)).findAll();
	}

}

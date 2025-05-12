package com.IT3180.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }
    
    @Autowired
    private UserService userService;

    /**
     * Gửi email hàng loạt đến tất cả cư dân trong database
     */
    public void sendEmail(String filter, String subject, String body) throws MessagingException {
    	try {
			List<com.IT3180.model.User> users = userService.getAllUsers();
			users.removeIf(user -> user.getRoles().stream().anyMatch(role -> role.getId() == 1));
			List<String> emails = new ArrayList<>();;
			switch (filter) {
			case "overdue":
			    // Xử lý trường hợp "overdue"
			    for (com.IT3180.model.User user : users) {
			        // Xử lý mỗi user trong danh sách khi filter là "overdue"
			    	List<LocalDate> dueDates = userService.getDueDateById(user.getId());
			    	LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
			    	for(LocalDate dueDate : dueDates) {
			    		if(dueDate.isBefore(currentDate)) {
			    			emails.add(user.getEmail());
			    			break;
			    		}
			    	}
			    }
			    break;
			case "emergency":
			    // Xử lý trường hợp "emergency"
			    for (com.IT3180.model.User user : users) {
			        // Xử lý mỗi user trong danh sách khi filter là "emergency"
			    	emails.add(user.getEmail());
			    }
			    break;
			case "all":
			    // Xử lý trường hợp "all"
			    for (com.IT3180.model.User user : users) {
			        // Xử lý mỗi user trong danh sách khi filter là "all"
			    	emails.add(user.getEmail());
			    }
			    break;
			default:
			    // Xử lý trường hợp mặc định nếu không khớp với bất kỳ case nào
				System.err.println("Filter không hợp lệ");
			    break;
			}
			int threadPoolSize = Math.min(emails.size(), 10); // Giới hạn số thread tối đa (ví dụ 10)
	        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
			for (String email : emails) {
				executor.submit(() -> {
	                try {
	                    MimeMessage message = mailSender.createMimeMessage();
	                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	                    helper.setTo(email);
	                    helper.setSubject(subject);
	                    helper.setText(body, true);

	                    mailSender.send(message);

	                    // Delay nhẹ nếu cần tránh bị đánh spam
	                    Thread.sleep(1000); // 1 giây
	                } catch (Exception e) {
	                    System.err.println("Gửi thất bại đến " + email + ": " + e.getMessage());
	                }
	            });
			}
			executor.shutdown();
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
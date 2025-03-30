package com.IT3180.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.IT3180.model.User;
import com.IT3180.services.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping ("/login")
	public String loginForm ()
	{
		return "auth/login";
	}
	 @GetMapping("/admin")
	    public String adminPage(Model model)
	 {
	    	List<User> users = userService.getAllUsers();
	    	model.addAttribute("users", users);
	        return "admin/admin_dashboard";
	 }
	 @GetMapping("/user")
	 public String userPage()
	 {
		 return "user/user_dashboard"; 
	 }		
}

package com.IT3180.dto;

import java.util.ArrayList;
import java.util.List;

import com.IT3180.model.Apartment;
import com.IT3180.model.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
	 private Long id;

	 @NotEmpty(message = "Nhập tên hợp lệ")
	 private String name;
	 
	 @NotEmpty(message = "Nhập email hợp lệ")
	 private String email;
	 
	 private List<Role> roles = new ArrayList<>();   
	 
	 @NotNull(message = "Phải chọn căn hộ")
	 private Apartment apartment;
	 
	 @NotEmpty
	 private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDTO(Long id, @NotEmpty(message = "Nhập tên hợp lệ") String name, @NotEmpty(message = "Nhập email hợp lệ") String email, List<Role> roles,
			@NotEmpty Apartment apartment, @NotEmpty String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
		this.apartment = apartment;
		this.password = password;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}

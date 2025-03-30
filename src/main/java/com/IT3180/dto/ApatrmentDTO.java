package com.IT3180.dto;

import com.IT3180.model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import com.IT3180.model.Resident;

public class ApatrmentDTO {
	
	private Long id;
	
	@NotEmpty
	private Long area;
	
	private User user;
	
	@NotNull
	private List<Resident> residents = new ArrayList<>();; 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Resident> getResidents() {
		return residents;
	}

	public void setResidents(List<Resident> residents) {
		this.residents = residents;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ApatrmentDTO(Long id, @NotEmpty Long area, User user, List<Resident> residents) {
		super();
		this.id = id;
		this.area = area;
		this.user = user;
		this.residents = residents;
	}

	public ApatrmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}

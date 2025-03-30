package com.IT3180.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bill_item")

public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_type_id", nullable = false)
    private BillType billType;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @Column(nullable = false)
    private Long quantity; // Số lượng tiêu thụ (VD: 10 m³ nước, 50 kWh điện)

    @Column(nullable = false)
    private Boolean status; // Trạng thái (Đã đóng, Chưa đóng)

    @Column(nullable = false)
    private LocalDate dueDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BillItem(Long id, BillType billType, Apartment apartment, Long quantity, Boolean status, LocalDate dueDate) {
		super();
		this.id = id;
		this.billType = billType;
		this.apartment = apartment;
		this.quantity = quantity;
		this.status = status;
		this.dueDate = dueDate;
	}

	public BillItem() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}


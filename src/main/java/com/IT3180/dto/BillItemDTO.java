package com.IT3180.dto;

import java.time.LocalDate;

public class BillItemDTO {
    private Long id;
    private String billTypeName; // Tên loại phí
    private Long apartmentId; // Tên căn hộ
    private Long quantity; // Lượng tiêu thụ
    private Boolean status; // Trạng thái (đã nộp/chưa nộp)
    private LocalDate dueDate; // Hạn nộp
    private Long totalAmount; // Tổng tiền
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillTypeName() {
		return billTypeName;
	}
	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}
	public Long getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
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
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BillItemDTO(Long id, String billTypeName, Long apartmentId, Long quantity, Boolean status, LocalDate dueDate,
			Long totalAmount) {
		super();
		this.id = id;
		this.billTypeName = billTypeName;
		this.apartmentId = apartmentId;
		this.quantity = quantity;
		this.status = status;
		this.dueDate = dueDate;
		this.totalAmount = totalAmount;
	}
	public BillItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}


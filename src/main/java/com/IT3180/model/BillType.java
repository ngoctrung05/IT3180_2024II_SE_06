package com.IT3180.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "bill_type")
public class BillType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Tên loại phí (VD: Nước, Điện, Phí dịch vụ,...)

    @Column(nullable = false)
    private String unit; // Đơn vị tính (m³, kWh, căn hộ,...)

    @Column(nullable = false)
    private Boolean isContribution;

    @Column(nullable = false)
    private Long pricePerUnit; // Giá tiền trên 1 đơn vị (VD: 10.000 VNĐ/m³)

    @OneToMany(mappedBy = "billType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> billItems = new ArrayList<>();

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Boolean getIsContribution() {
		return isContribution;
	}

	public void setIsContribution(Boolean isContribution) {
		this.isContribution = isContribution;
	}

	public Long getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}

	public BillType(Long id, String name, String unit, Boolean isContribution, Long pricePerUnit,
			List<BillItem> billItems) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.isContribution = isContribution;
		this.pricePerUnit = pricePerUnit;
		this.billItems = billItems;
	}

	public BillType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getContribution() {
		return isContribution;
	}

	public void setContribution(Boolean contribution) {
		isContribution = contribution;
	}
}
package com.IT3180.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.IT3180.dto.BillItemDTO;
import com.IT3180.model.Apartment;
import com.IT3180.model.BillItem;
import com.IT3180.model.BillType;
import com.IT3180.repository.ApartmentRepository;
import com.IT3180.repository.BillItemRepository;
import com.IT3180.repository.BillTypeRepository;
import java.time.temporal.ChronoUnit;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
	@Autowired
	private BillItemRepository billItemRepository;
	@Autowired
	private BillTypeRepository billTypeRepository;
	@Autowired
	private ApartmentRepository apartmentRepository;
	 // Thêm loại phí
    public void addBillType(BillType billType) {
        billTypeRepository.save(billType);
    }

    // Xóa loại phí
    public void deleteBillType(Long id) {
        billTypeRepository.deleteById(id);
    }

    public List<BillType> getAllBillTypes() {
        return billTypeRepository.findAll();
    }

    // Lấy danh sách loại phí
    public List<BillType> getAllFeeTypes() {
        return billTypeRepository.findByIsContribution(false);
    }

    // Lấy danh sách loại đóng góp
    public List<BillType> getAllContributionType() {
        return billTypeRepository.findByIsContribution(true);
    }

    // Thêm khoản phí
    public BillItem addBillItem(BillItemDTO billItemDTO) {
        BillItem billItem = toEntity(billItemDTO);
        return billItemRepository.save(billItem);
    }

    // Xóa khoản phí
    public void deleteBillItem(Long id) {
        billItemRepository.deleteById(id);
    }

    // Lọc danh sách khoản phí
    public List<BillItemDTO> getBillItems(String BillTypeName, Long apartmentId, Boolean status, LocalDate fromDate, LocalDate toDate) {
        List<BillItem> billItems = billItemRepository.findByFilters(BillTypeName, apartmentId, status, fromDate, toDate);
        return billItems.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BillItemDTO toDTO(BillItem billItem) {
        return new BillItemDTO(
                billItem.getId(),
                billItem.getBillType().getName(),
                billItem.getApartment().getId(),
                billItem.getQuantity(),
                billItem.getStatus(),
                billItem.getDueDate(),
                billItem.getQuantity() * billItem.getBillType().getPricePerUnit()
        );
    }

    public BillItem toEntity(BillItemDTO billItemDTO) {
        Apartment apartment = apartmentRepository.findById(billItemDTO.getApartmentId())
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        BillType billType = billTypeRepository.findByName(billItemDTO.getBillTypeName())
                .orElseThrow(() -> new RuntimeException("BillType not found"));

        BillItem billItem = new BillItem();
        billItem.setApartment(apartment);
        billItem.setBillType(billType);
        billItem.setQuantity(billItemDTO.getQuantity());
        billItem.setStatus(billItemDTO.getStatus());
        billItem.setDueDate(billItemDTO.getDueDate());
        return billItem;
    }
    
    public Long getAllBillType ()
    {
    	return billTypeRepository.count();
    }
    
    @Scheduled(cron = "0 0 0 * * ?")  // Chạy mỗi ngày lúc 00:00
    public void deleteBillItemsByStatusAndDueDate() {
        LocalDate currentDate = LocalDate.now();  // Lấy ngày hiện tại
        LocalDate dueDateThreshold = currentDate.minusDays(45);  // Tính ngày đã qua 45 ngày

        // Tìm tất cả các BillItem có status = 1 và dueDate đã qua 45 ngày
        List<BillItem> billItems = billItemRepository.findByStatusAndDueDateBefore(1, dueDateThreshold);

        if (!billItems.isEmpty()) {
            billItemRepository.deleteAll(billItems);  // Xóa tất cả các BillItem thỏa mãn điều kiện
        }
    }
}

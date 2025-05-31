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
import com.IT3180.model.User;
import com.IT3180.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
	 // Thêm loại phí
    public void addBillType(BillType billType) {
        billTypeRepository.save(billType);
    }

    // Xóa loại phí
    public void deleteBillType(Long id) {
        billTypeRepository.deleteById(id);
    }

    // Đánh dấu loại phí từ chưa nộp thành đã nộp
    public void updateBillItem(Long id) {
        BillItem billItem = billItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("BillItem not found"));
        billItem.setStatus(true);
        billItemRepository.save(billItem);
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

    public List<BillItemDTO> getFeeItems(String BillTypeName, Long apartmentId, Boolean status, LocalDate fromDate, LocalDate toDate) {
        List<BillItem> billItems = billItemRepository.findByFilters(BillTypeName, apartmentId, status, fromDate, toDate);
        
        if (billItems != null) {
            billItems.removeIf(billItem -> 
                billItem.getBillType() != null && Boolean.TRUE.equals(billItem.getBillType().getContribution())
            );
        }
        
        return billItems.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<BillItemDTO> getContributionItems(String BillTypeName, Long apartmentId, Boolean status, LocalDate fromDate, LocalDate toDate) {
        List<BillItem> billItems = billItemRepository.findByFilters(BillTypeName, apartmentId, status, fromDate, toDate);
        
        if (billItems != null) {
            billItems.removeIf(billItem -> 
                billItem.getBillType() != null && Boolean.FALSE.equals(billItem.getBillType().getContribution())
            );
        }
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

    private void autoCreateServiceFee(User user) {
        Apartment apartment = user.getApartment();
        if(apartment == null) {
            return;
        }
        Double area = apartment.getArea();
        long quantity = area.longValue();
        BillType serviceFeeType = billTypeRepository.findByName("Phí dịch vụ chung cư")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phí 'Phí dịch vụ chung cư'"));

        BillItem billItem = new BillItem();
        billItem.setApartment(apartment);
        billItem.setBillType(serviceFeeType);
        billItem.setQuantity(quantity); // Số lượng chính là diện tích m2
        billItem.setStatus(false); // chưa nộp
        billItem.setDueDate(LocalDate.now().withDayOfMonth(10)); // hạn nộp là ngày 10 tháng này

        billItemRepository.save(billItem);
    }

    private void autoCreateManageFee(User user) {
        Apartment apartment = user.getApartment();
        if(apartment == null) {
            return;
        }
        Double area = apartment.getArea();
        long quantity = area.longValue();
        BillType serviceFeeType = billTypeRepository.findByName("Phí quản lý chung cư")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại phí 'Phí quản lý chung cư'"));

        BillItem billItem = new BillItem();
        billItem.setApartment(apartment);
        billItem.setBillType(serviceFeeType);
        billItem.setQuantity(quantity); // Số lượng chính là diện tích m2
        billItem.setStatus(false); // chưa nộp
        billItem.setDueDate(LocalDate.now().withDayOfMonth(10)); // hạn nộp là ngày 10 tháng này

        billItemRepository.save(billItem);
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    public void createFixedBills() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            autoCreateManageFee(user);
            autoCreateServiceFee(user);
        }
    }
}

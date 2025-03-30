package com.IT3180.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.IT3180.model.Apartment;
import com.IT3180.repository.ApartmentRepository;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;
    
    public long getTotalApartments() {
        return apartmentRepository.count();
    }
    
    public long getEmptyApartmentCount() {
        return apartmentRepository.countEmptyApartments();
    }

    // Lấy danh sách tất cả căn hộ
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    // Lấy căn hộ theo ID
    public Optional<Apartment> getApartmentById(Long id) {
        return apartmentRepository.findById(id);
    }

    // Tạo mới căn hộ (Kiểm tra nếu đã tồn tại)
    public Apartment createApartment(Apartment apartment) {
        if (apartment.getId() != null && apartmentRepository.existsById(apartment.getId())) {
            throw new RuntimeException("Apartment with ID " + apartment.getId() + " already exists");
        }
        return apartmentRepository.save(apartment);
    }
    
    //Chỉnh sửa căn hộ
    public Apartment updateApartment(Long id, Long IdHouseholder, String phone) {
    	Apartment new_apartment = getApartmentById(id).get();
    	new_apartment.setIdHouseholder(IdHouseholder);
    	new_apartment.setPhone(phone);
    	return apartmentRepository.save(new_apartment);
    }

    // Xóa căn hộ
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }
}

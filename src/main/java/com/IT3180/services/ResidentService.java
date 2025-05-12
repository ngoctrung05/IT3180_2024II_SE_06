package com.IT3180.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.IT3180.model.Resident;
import com.IT3180.repository.ResidentRepository;
import jakarta.persistence.EntityNotFoundException;
import com.IT3180.model.BillItem;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    
    public long getTotalResidents() {
        return residentRepository.count();
    }

    // Lấy danh sách tất cả cư dân
    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    // Lấy cư dân theo ID
    public Optional<Resident> getResidentById(Long id) {
        return residentRepository.findById(id);
    }

    // Tạo mới cư dân
    public Resident createResident(Resident resident) {
        // Có thể thêm logic kiểm tra nếu cần
        return residentRepository.save(resident);
    }

    // Cập nhật cư dân
    public Resident updateResident(Resident resident) {
        if (resident.getId() == null || !residentRepository.existsById(resident.getId())) {
            throw new EntityNotFoundException("Resident not found with id: " + resident.getId());
        }
        return residentRepository.save(resident);
    }

    // Xóa cư dân theo ID
    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }

    // Tìm cư dân theo ID căn hộ
    public List<Resident> findResidentsByApartmentId(Long apartmentId) {
        return residentRepository.findByApartmentId(apartmentId);
    }

    // Tìm cư dân theo status (ví dụ: "active")
    public List<Resident> findResidentsByStatus(String status) {
        return residentRepository.findByStatus(status);
    }

    // Tìm cư dân theo CCCD
    public Resident findResidentByCccd(String cccd) {
        return residentRepository.findByCccd(cccd);
    }
}

package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.IT3180.model.Resident;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ResidentRepository extends JpaRepository <Resident, Long> {
    // Đếm tổng số cư dân
    long count();

    // Tìm cư dân theo căn hộ
    List<Resident> findByApartmentId(Long apartmentId);

    // Tìm cư dân còn ở (status = "active")
    List<Resident> findByStatus(String status);

    // Tìm cư dân theo CCCD
    Resident findByCccd(String cccd);
    
    // Lấy email
    @Query("SELECT r.email FROM Resident r")
    List<String> getAllResidentEmails();
}

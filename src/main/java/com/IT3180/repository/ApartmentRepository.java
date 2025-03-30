package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.IT3180.model.Apartment;
import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository <Apartment, Long> 
{
    // Đếm số lượng căn hộ
    long count();

    // Tìm các căn hộ trống (không có cư dân nào)
    List<Apartment> findByResidentsIsEmpty();

    // Tìm các căn hộ có cư dân
    List<Apartment> findByResidentsIsNotEmpty();

    // Tìm căn hộ theo id của chủ hộ (householder)
    List<Apartment> findByIdHouseholder(Long idHouseholder);
    
    @Query("SELECT COUNT(a) FROM Apartment a WHERE a.idHouseholder IS NULL OR a.phone IS NULL")
    long countEmptyApartments();
    
    
}

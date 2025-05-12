package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.IT3180.model.BillItem;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {
    List<BillItem> findByStatus(Boolean status);

    @Query("SELECT f FROM BillItem f " +
            "WHERE (:billTypeName IS NULL OR f.billType.name LIKE CONCAT('%', :billTypeName, '%')) " +
            "AND (:apartmentId IS NULL OR f.apartment.id = :apartmentId) " +
            "AND (:status IS NULL OR f.status = :status) " +
            "AND (:fromDate IS NULL OR f.dueDate >= :fromDate) " +
            "AND (:toDate IS NULL OR f.dueDate <= :toDate)")
    List<BillItem> findByFilters(@Param("billTypeName") String billTypeName,
                                 @Param("apartmentId") Long apartmentId,
                                 @Param("status") Boolean status,
                                 @Param("fromDate") LocalDate fromDate,
                                 @Param("toDate") LocalDate toDate);
    
    @Query("SELECT b.dueDate FROM BillItem b WHERE b.apartment.id = (SELECT u.apartment.id FROM User u WHERE u.id = :userId)")
    List<LocalDate> findDueDatesByUserId(@Param("userId") Long userId);
    
 // Tìm các BillItem có status = 1 và dueDate trước ngày được cung cấp
    List<BillItem> findByStatusAndDueDateBefore(int status, LocalDate dueDate);
}

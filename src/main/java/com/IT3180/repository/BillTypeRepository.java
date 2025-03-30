package com.IT3180.repository;

import com.IT3180.model.BillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long> {
    boolean existsByName(String name);
    Optional<BillType> findByName(String name);
    List<BillType> findByIsContribution(Boolean isContribution);
}


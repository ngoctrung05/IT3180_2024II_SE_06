package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.IT3180.model.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> 
{
	Role findByName(String name);
}

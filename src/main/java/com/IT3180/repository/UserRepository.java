package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.IT3180.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>
{
	User findByName (String name);
	User findByEmail (String email);
	boolean existsByName(String name);
	List<User> findUsersByRoles_Name( String roleName);
}

package com.IT3180.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event.ID;

import com.IT3180.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>
{
	User findByName (String name);
	
	boolean existsByName(String name);
}

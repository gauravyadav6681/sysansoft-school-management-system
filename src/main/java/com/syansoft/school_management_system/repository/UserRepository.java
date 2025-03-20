package com.syansoft.school_management_system.repository;


import com.syansoft.school_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); 

	Optional<User> findByEmail(String username);

	
}


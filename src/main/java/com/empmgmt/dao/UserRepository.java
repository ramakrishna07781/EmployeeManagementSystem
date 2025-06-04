package com.empmgmt.dao;

import com.empmgmt.enums.UserRole;
import com.empmgmt.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("SELECT u FROM Users u WHERE u.role = :role AND SIZE(u.assignedTasks) = 0")
    List<Users> findUnassignedEmployees(UserRole role);

    Optional<Users> findByEmailId(String email);
}

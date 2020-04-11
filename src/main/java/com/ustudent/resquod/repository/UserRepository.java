package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.LoginUserData;
import com.ustudent.resquod.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT new com.ustudent.resquod.model.LoginUserData(u.email, u.password, u.role) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<LoginUserData> findUserPassword(String email);

    List<User> findAll();
}

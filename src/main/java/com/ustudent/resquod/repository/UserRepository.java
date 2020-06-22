package com.ustudent.resquod.repository;

import com.ustudent.resquod.model.User;
import com.ustudent.resquod.model.dao.LoginUserData;
import com.ustudent.resquod.model.dao.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT new com.ustudent.resquod.model.dao.LoginUserData(u.email, u.password, u.role) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<LoginUserData> findUserPassword(String email);

    @Query(value = "SELECT new com.ustudent.resquod.model.dao.UserData(u.email,u.role, u.name, u.surname) " +
            "FROM  User u WHERE u.email = ?1")
    Optional<UserData> findUserData(String email);

    @Query(value = "SELECT new com.ustudent.resquod.model.dao.UserData(u.email,u.role, u.name, u.surname) FROM  User u " +
            "INNER JOIN u.events e ON e.id = ?1")
    List<UserData> findUserDataByEventId(Long eventId);

    List<User> findAll();
}

package com.preproject.springbootcrud.repository;

import com.preproject.springbootcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastName);
    User findByUserName(String userName);
    User findByEmail(String email);
    //Optional<User> findById(long id);
    User findById(long id);

    @Modifying
    @Query("update User u set u = ?2 where u.id = ?1")
    int updateUserById(long id, User user);

    @Modifying
    @Transactional
    void deleteUserById(long id);
}

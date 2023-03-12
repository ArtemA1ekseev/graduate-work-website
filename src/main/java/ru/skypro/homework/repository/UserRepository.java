package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Users;
import java.util.List;
import java.util.Optional;

/**
 * Repository UserRepository (users/пользователь).
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "select * from users order by id", nativeQuery = true)
    List<Users> findAllUsers();

    Optional<Users> findByUsername(String userName);
}
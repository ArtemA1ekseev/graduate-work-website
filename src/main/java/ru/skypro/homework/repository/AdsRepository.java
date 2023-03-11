package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;
import java.util.List;
/**
 * Repository AdsRepository (advertisement/объявление).
 */
@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    @Query(value = "select * from ads order by id", nativeQuery = true)
    List<Ads> findAllAdverts();

    @Query(value = "select * from ads where lower(title) like lower(concat('%', ?1,'%'))", nativeQuery = true)
    List<Ads> findAds(String search);
}
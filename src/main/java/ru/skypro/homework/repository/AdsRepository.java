package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ads;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Long> {

    List<Ads> findByTitleContainingIgnoreCase(String title);
}
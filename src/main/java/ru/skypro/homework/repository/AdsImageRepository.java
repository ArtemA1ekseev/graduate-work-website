package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsImage;

import java.util.List;

/**
 * Repository AdsImageRepository (advertisement image/изображение в объявлениях).
 */
public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {
    void deleteAllByAdsId(Integer adsId);
}

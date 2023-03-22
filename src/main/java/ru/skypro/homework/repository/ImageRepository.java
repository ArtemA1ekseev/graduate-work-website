package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Image;

/**
 * Repository ImageRepository (advertisement image/изображение в объявлениях).
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByAdsId(long adsId);
}
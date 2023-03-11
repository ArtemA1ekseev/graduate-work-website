package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsImage;
/**
 * Repository AdsImageRepository (advertisement image/изображение в объявлениях).
 */
public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {
}

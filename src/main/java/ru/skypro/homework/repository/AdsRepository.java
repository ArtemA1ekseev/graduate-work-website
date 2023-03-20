package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ads;

import java.util.Collection;

public interface AdsRepository extends JpaRepository<Ads, Long> {

    Collection<Ads> findAllByAuthorId(long id);
}
package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

import java.util.Collection;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    Collection <Ads> findByTitle (String title);
    Collection <Ads> findAdsByAuthor (User author);

    Collection <Ads> findAdsByDescriptionContains (String part);



}

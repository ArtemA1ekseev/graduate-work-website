package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsComment;

import java.util.Collection;
import java.util.Optional;

public interface AdsCommentRepository extends JpaRepository<AdsComment, Long> {

    Collection<AdsComment> findAllByAdsId(long adsId);

    Optional<AdsComment> findByIdAndAdsId(long id, long adsId);
}
package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsComment;

import java.util.List;

public interface AdsCommentRepository extends JpaRepository<AdsComment, Long> {

    List<AdsComment> findAllByAdsId(long adId);
}

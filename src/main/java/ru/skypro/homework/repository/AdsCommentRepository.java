package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdsComment;
import java.util.List;
import java.util.Optional;
/**
 * Repository AdsCommentRepository (advertisement comment/комментарий в объявлениях).
 */
@Repository
public interface AdsCommentRepository extends JpaRepository<AdsComment, Integer> {

    @Query(value = "select c from Comment c where c.ads.id = ?1 and c.id = ?2", nativeQuery = true)
    Optional<AdsComment> findAdsComment(Integer adsId, Integer id);

    @Query(value = "select c from Comment c where c.ads.id = ?1 and c.id = ?2", nativeQuery = true)
    Optional<AdsComment> deleteAdsComment(Integer adsId, Integer id);

    List<AdsComment> findAllByAdsId(Integer adsId);

    List<AdsComment> findAllByAdsIdOrderByIdDesc(Integer adsId);
}
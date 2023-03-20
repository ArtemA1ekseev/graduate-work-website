package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AdsServiceImpl implements AdsService {
    Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);


    private final AdsRepository adsRepository;

    private final AdsCommentRepository adsCommentRepository;

    private final UserRepository userRepository;

    private final ImagesRepository imagesRepository;

    @Override
    public Ads createAds(Ads ads) {
        logger.info("Was invoked method for create ad");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        ads.setAuthor(user);
        logger.info("ad created");
        return adsRepository.save(ads);
    }

    @Override
    public Ads getAds(long id) {
        logger.info("Was invoked method for get ad by id");
        return adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
    }

    @Override
    public Collection<Ads> getAllAds() {
        logger.info("Was invoked method for get all ads");
        return adsRepository.findAll();
    }

    @Override
    public boolean removeAds(long id, Authentication authentication) {
        logger.info("Was invoked method for delete ad by id");
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        logger.warn("Ad by id {} not found", id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            List<Long> adsComments = adsCommentRepository.findAll().stream()
                    .filter(adsComment -> adsComment.getAds().getId() == ads.getId())
                    .map(AdsComment::getId)
                    .collect(Collectors.toList());
            adsCommentRepository.deleteAllById(adsComments);
            ads.getImage().setAds(null);
            adsRepository.delete(ads);
            imagesRepository.deleteById(ads.getImage().getId());
            logger.info("ad deleted");
            return true;
        }
        return false;
    }

    @Override
    public Ads updateAds(long id, Ads updatedAdsDto, Authentication authentication) {
        logger.info("Was invoked method for update ad by id");
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        logger.warn("Ad by id {} not found", id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            updatedAdsDto.setAuthor(ads.getAuthor());
            updatedAdsDto.setId(ads.getId());
            updatedAdsDto.setImage(ads.getImage());
            return adsRepository.save(updatedAdsDto);
        }
        logger.info("ad updated");
        return updatedAdsDto;
    }

    @Override
    public Collection<Ads> getAdsMe() {
        logger.info("Was invoked method for get all my ads");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        return adsRepository.findAll().stream()
                .filter(ads -> ads.getAuthor().equals(user)).collect(Collectors.toList());
    }

    @Override
    public AdsComment addAdsComment(long adKey, AdsComment adsComment) {
        logger.info("Was invoked method for add ads comment");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        adsComment.setAuthor(user);
        adsComment.setAds(adsRepository.findById(adKey).orElseThrow());
        adsComment.setCreatedAt(LocalDateTime.now());
        logger.info("comment added");
        return adsCommentRepository.save(adsComment);
    }

    @Override
    public Collection<AdsComment> getAdsComments(long adKey) {
        logger.info("Was invoked method for get ads comment by adKey");
        return adsCommentRepository.findAll().stream().filter(adsComment -> adsComment.getAds().getId() == adKey)
                .collect(Collectors.toList());
    }

    @Override
    public AdsComment getAdsComment(long adKey, long id) {
        logger.info("Was invoked method for get ads comment by adKey and id");
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        logger.warn("Comment by id {} not found", id);
        if (adsComment.getAds().getId() != adKey) {
            logger.warn("Comment by id {} does not belong to ad by id {} ", id, adKey);
            throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
        }
        return adsComment;
    }

    @Override
    public boolean deleteAdsComment(long adKey, long id, Authentication authentication) {
        logger.info("Was invoked method for delete ads comment by adKey and id");
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        logger.warn("Comment by {} not found", id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (adsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            if (adsComment.getAds().getId() != adKey) {
                logger.warn("Comment by id {} does not belong to ad by id {} ", id, adKey);
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
            }
            adsCommentRepository.delete(adsComment);
            logger.info("comment deleted");
            return true;
        }
        logger.warn("comment not deleted");
        return false;
    }

    @Override
    public AdsComment updateAdsComment(long adKey, long id, AdsComment updatedAdsComment, Authentication authentication) {
        logger.info("Was invoked method for update ads comment by adKey and id");
        AdsComment updateAdsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        logger.warn("Comment by id {} not found", id);
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updateAdsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            if (updateAdsComment.getAds().getId() != adKey) {
                logger.warn("Comment by id {} does not belong to ad by id {} ", id, adKey);
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
            }
            updateAdsComment.setText(updatedAdsComment.getText());
            logger.warn("comment updated");
            return adsCommentRepository.save(updateAdsComment);
        }
        return updateAdsComment;
    }

    @SneakyThrows
    @Override
    public Ads updateAdsImage(Ads ads, Authentication authentication, Images image) {
        logger.info("Was invoked method for update ads image");

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            logger.warn("image updated");
            return adsRepository.save(ads);
        }
        return ads;
    }
}
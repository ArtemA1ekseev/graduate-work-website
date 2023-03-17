package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    private final AdsCommentRepository adsCommentRepository;

    private final UserRepository userRepository;

    private final ImagesRepository imagesRepository;

    @Override
    public Ads createAds(Ads ads) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        ads.setAuthor(user);
        return adsRepository.save(ads);
    }

    @Override
    public Ads getAds(long id) {
        return adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
    }

    @Override
    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public boolean removeAds(long id, Authentication authentication) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
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
            return true;
        }
        return false;
    }

    @Override
    public Ads updateAds(long id, Ads updatedAdsDto, Authentication authentication) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            updatedAdsDto.setAuthor(ads.getAuthor());
            updatedAdsDto.setId(ads.getId());
            updatedAdsDto.setImage(ads.getImage());
            return adsRepository.save(updatedAdsDto);
        }
        return updatedAdsDto;
    }

    @Override
    public Collection<Ads> getAdsMe() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        return adsRepository.findAll().stream()
                .filter(ads -> ads.getAuthor().equals(user)).collect(Collectors.toList());
    }

    @Override
    public AdsComment addAdsComment(long adKey, AdsComment adsComment) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        adsComment.setAuthor(user);
        adsComment.setAds(adsRepository.findById(adKey).orElseThrow());
        adsComment.setCreatedAt(LocalDateTime.now());
        return adsCommentRepository.save(adsComment);
    }

    @Override
    public Collection<AdsComment> getAdsComments(long adKey) {
        return adsCommentRepository.findAll().stream().filter(adsComment -> adsComment.getAds().getId() == adKey)
                .collect(Collectors.toList());
    }

    @Override
    public AdsComment getAdsComment(long adKey, long id) {
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        if (adsComment.getAds().getId() != adKey) {
            throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
        }
        return adsComment;
    }

    @Override
    public boolean deleteAdsComment(long adKey, long id, Authentication authentication) {
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (adsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            if (adsComment.getAds().getId() != adKey) {
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
            }
            adsCommentRepository.delete(adsComment);
            return true;
        }
        return false;
    }

    @Override
    public AdsComment updateAdsComment(long adKey, long id, AdsComment updatedAdsComment, Authentication authentication) {
        AdsComment updateAdsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updateAdsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            if (updateAdsComment.getAds().getId() != adKey) {
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
            }
            updateAdsComment.setText(updatedAdsComment.getText());
            return adsCommentRepository.save(updateAdsComment);
        }
        return updateAdsComment;

    }

    @SneakyThrows
    @Override
    public Ads updateAdsImage(Ads ads, Authentication authentication, Images image) {


//      Ads ads = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {

            return adsRepository.save(ads);

        }

        return ads;
    }
}
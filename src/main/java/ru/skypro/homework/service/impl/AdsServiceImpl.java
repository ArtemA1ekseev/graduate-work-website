package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    private final AdsCommentRepository adsCommentRepository;

    private final UserRepository userRepository;

    public AdsServiceImpl(AdsRepository adsRepository, AdsCommentRepository adsCommentRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.adsCommentRepository = adsCommentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ads createAds(Ads ads) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();

        ads.setAuthor(user);
        return adsRepository.save(ads);
    }

    @Override
    public Ads getFullAds(long id) {
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
        if(ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")){
            List<Long> adsComments = adsCommentRepository.findAll().stream()
                    .filter(adsComment -> adsComment.getAds().getId() == ads.getId())
                    .map(AdsComment::getId)
                    .collect(Collectors.toList());
            adsCommentRepository.deleteAllById(adsComments);
            adsRepository.delete(ads);
            return true;
        }
        return false;
    }

    @Override
    public Ads updateAds(long id, Ads updatedAdsDto, Authentication authentication) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if(ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")){
            updatedAdsDto.setAuthor(ads.getAuthor());
            updatedAdsDto.setId(ads.getId());
//            updatedAdsDto.setDescription(ads.getDescription());
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
    public AdsComment addAdsComment(long ad_pk, AdsComment adsComment) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        adsComment.setAuthor(user);
        adsComment.setAds(adsRepository.findById(ad_pk).orElseThrow());
        adsComment.setCreatedAt(LocalDateTime.now());
        return adsCommentRepository.save(adsComment);
    }

    @Override
    public Collection<AdsComment> getAdsComments(long ad_pk) {
        return adsCommentRepository.findAll().stream().filter(adsComment -> adsComment.getAds().getId() == ad_pk)
                .collect(Collectors.toList());
    }

    @Override
    public AdsComment getAdsComment(long ad_pk, long id) {
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        if (adsComment.getAds().getId() != ad_pk){
            throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + ad_pk);
        }
        return adsComment;
    }

    @Override
    public boolean deleteAdsComment(long ad_pk, long id, Authentication authentication) {
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if(adsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")){
            if (adsComment.getAds().getId() != ad_pk){
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + ad_pk);
            }
            adsCommentRepository.delete(adsComment);
            return true;
        }
        return false;
    }

    @Override
    public AdsComment updateAdsComment(long ad_pk, long id, AdsComment updatedAdsComment, Authentication authentication) {
        AdsComment updateAdsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if(updateAdsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")){
            if (updateAdsComment.getAds().getId() != ad_pk){
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + ad_pk);
            }
            updateAdsComment.setText(updatedAdsComment.getText());
            return adsCommentRepository.save(updateAdsComment);
        }
        return updateAdsComment;
    }
}
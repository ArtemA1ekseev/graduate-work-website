package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImagesService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    private final AdsCommentRepository adsCommentRepository;

    private final UserRepository userRepository;

    private final ImagesService imagesService;

    private final AdsMapper adsMapper;

    private final AdsCommentMapper adsCommentMapper;

    @Override
    public AdsDto createAds(CreateAdsDto createAdsDto, MultipartFile imageFile) throws IOException {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();

        Ads ads = adsMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        ads.setImage(imagesService.uploadImage(imageFile, adsRepository.save(ads)));
        return adsMapper.toDto(adsRepository.save(ads));
    }

    @Override
    public Ads getAds(long id) {
        return adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
    }

    @Override
    public FullAdsDto getFullAdsDto(long id) {
        return adsMapper.toFullAdsDto(adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!")));
    }

    @Override
    public List<AdsDto> getAllAds() {
        return adsMapper.toDto(adsRepository.findAll());
    }

    @Override
    public boolean removeAds(long id, Authentication authentication) throws IOException{
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (ads.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            List<Long> adsComments = adsCommentRepository.findAll().stream()
                    .filter(adsComment -> adsComment.getAds().getId() == ads.getId())
                    .map(AdsComment::getId)
                    .collect(Collectors.toList());
            adsCommentRepository.deleteAllById(adsComments);
            imagesService.removeImage(ads.getImage().getId());
            adsRepository.delete(ads);
            return true;
        }
        return false;
    }

    @Override
    public AdsDto updateAds(long id, AdsDto updateAdsDto, Authentication authentication) {
        Ads updatedAds = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с id " + id + " не найдено!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedAds.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            updatedAds.setTitle(updateAdsDto.getTitle());
            updatedAds.setDescription(updateAdsDto.getDescription());
            updatedAds.setPrice(updateAdsDto.getPrice());
            adsRepository.save(updatedAds);
            return adsMapper.toDto(updatedAds);
        }
        return updateAdsDto;
    }

    @Override
    public List<AdsDto> getAdsMe() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        List<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        return adsMapper.toDto(adsList);
    }

    @Override
    public AdsCommentDto addAdsComment(long adKey, AdsCommentDto adsCommentDto) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        AdsComment adsComment = adsCommentMapper.toEntity(adsCommentDto);
        adsComment.setAuthor(user);
        adsComment.setAds(adsRepository.findById(adKey).orElseThrow());
        adsComment.setCreatedAt(LocalDateTime.now());
        adsCommentRepository.save(adsComment);
        return adsCommentMapper.toDto(adsComment);
    }

    @Override
    public List<AdsCommentDto> getAdsComments(long adKey) {
        List<AdsComment> commentList = adsCommentRepository.findAllByAdsId(adKey);
        return adsCommentMapper.toDto(commentList);
    }

    @Override
    public AdsCommentDto getAdsComment(long adKey, long id) {
        AdsComment adsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        if (adsComment.getAds().getId() != adKey) {
            throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
        }
        return adsCommentMapper.toDto(adsComment);
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
    public AdsCommentDto updateAdsComment(long adKey, long id, AdsCommentDto updateAdsComment, Authentication authentication) {
        AdsComment updatedAdsComment = adsCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Комментарий с id " + id + " не найден!"));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        if (updatedAdsComment.getAuthor().getEmail().equals(user.getEmail()) || user.getRole().equals("ADMIN")) {
            if (updatedAdsComment.getAds().getId() != adKey) {
                throw new NotFoundException("Комментарий с id " + id + " не принадлежит объявлению с id " + adKey);
            }
            updatedAdsComment.setText(updateAdsComment.getText());
            adsCommentRepository.save(updatedAdsComment);
            return adsCommentMapper.toDto(updatedAdsComment);
        }
        return updateAdsComment;
    }
}
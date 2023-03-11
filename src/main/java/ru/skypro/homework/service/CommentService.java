package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;
/**
 * Interface CommentService (advertisement comment/комментарий в объявлениях).
 */
public interface CommentService {

    AdsCommentDto createComment(Integer adsId, AdsCommentDto adsCommentDto);

    AdsCommentDto getAdsComment(Integer adsId, Integer id);

    ResponseWrapperAdsComment getAdsAllComments(Integer adsId);

    void deleteAdsComment(Integer adsId, Integer id);

    AdsCommentDto updateAdsComment(Integer adsPk, Integer pk, AdsCommentDto adsCommentDto);

}
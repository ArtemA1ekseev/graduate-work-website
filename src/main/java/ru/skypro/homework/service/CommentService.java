package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;

public interface CommentService {

    CommentDto createComment(Integer adsId, CommentDto adsCommentDto);

    CommentDto getAdsComment(Integer adsId, Integer id);

    ResponseWrapperAdsComment getAdsAllComments(Integer adsId);

    void deleteAdsComment(Integer adsId, Integer id);

    CommentDto updateAdsComment(Integer adsPk, Integer pk, CommentDto adsCommentDto);

}
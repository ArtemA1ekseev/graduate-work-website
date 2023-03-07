package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsCommentDto;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final AdsCommentRepository adsCommentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(AdsCommentRepository adsCommentRepository, AdsRepository adsRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.adsCommentRepository = adsCommentRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public AdsCommentDto createComment(Integer adsId, AdsCommentDto adsCommentDto) {
        AdsComment createdAdsComment = commentMapper.adsCommentDtoToCommentEntity(adsCommentDto);
        createdAdsComment.setUsers(userRepository.findById(adsCommentDto.getAuthor()).orElseThrow(UserNotFoundException::new));
        createdAdsComment.setAds(adsRepository.findById(adsId).orElseThrow(AdvertNotFoundException::new));
        adsCommentRepository.save(createdAdsComment);
        return adsCommentDto;
    }

    @Override
    public void deleteAdsComment(Integer adsId, Integer id) {
        AdsComment adsComment = adsCommentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        adsCommentRepository.delete(adsComment);
    }

    @Override
    public ResponseWrapperAdsComment getAdsAllComments(Integer adsId) {
        List<AdsCommentDto> adsCommentDtoList = commentMapper.commentEntitiesToAdsCommentDtos(adsCommentRepository.findAllByAdsIdOrderByIdDesc(adsId));
        ResponseWrapperAdsComment responseWrapperAdsComment = new ResponseWrapperAdsComment();
        responseWrapperAdsComment.setCount(adsCommentDtoList.size());
        responseWrapperAdsComment.setResults(adsCommentDtoList);
        return responseWrapperAdsComment;
    }

    @Override
    public AdsCommentDto getAdsComment(Integer adsId, Integer id) {
        AdsComment adsComment = adsCommentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        return commentMapper.commentEntityToAdsCommentDto(adsComment);
    }

    @Override
    public AdsCommentDto updateAdsComment(Integer adsId, Integer id, AdsCommentDto adsCommentDto) {
        AdsComment adsComment = adsCommentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        adsComment.setCreatedAt(adsCommentDto.getCreatedAt());
        adsComment.setText(adsCommentDto.getText());
        adsCommentRepository.save(adsComment);
        return adsCommentDto;
    }
}
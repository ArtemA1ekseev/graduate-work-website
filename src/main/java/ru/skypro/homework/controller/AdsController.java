package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@EnableMethodSecurity
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private final AdsMapper mapper;

    private final AdsCommentMapper commentMapper;

    private final AdsService adsService;

    public AdsController(AdsMapper mapper, AdsCommentMapper commentMapper, AdsService adsService) {
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.adsService = adsService;
    }

    @Operation(summary = "getAllAds", description = "getAllAds")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        Collection<Ads> listAds = adsService.getAllAds();
        return ResponseWrapper.of(mapper.toDto(listAds));
    }

    //Надо будет настроить сохранение картинок
//  @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
    @Operation(summary = "addAds", description = "addAds")
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public AdsDto addAds(@RequestBody CreateAdsDto dto) {
        Ads ads = mapper.toEntity(dto);
        return mapper.toDto(adsService.createAds(ads));
    }

    @Operation(summary = "getAdsMe", description = "getAdsMe")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {
        Collection<Ads> listAds = adsService.getAdsMe();
        return ResponseWrapper.of(mapper.toDto(listAds));
    }

    @Operation(summary = "removeAds", description = "removeAds")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable long id, Authentication authentication) {

        if (adsService.removeAds(id, authentication)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "getAds", description = "getAds")
    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable long id) {
        return mapper.toFullAdsDto(adsService.getFullAds(id));
    }

    @Operation(summary = "updateAds", description = "updateAds")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, @RequestBody AdsDto updatedAdsDto,
                                            Authentication authentication) {

        AdsDto updateAdsDto = mapper.toDto(adsService.updateAds(id, mapper.toEntity(updatedAdsDto), authentication));

        if (updateAdsDto.equals(updatedAdsDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "getAdsComments", description = "getAdsComments")
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<AdsCommentDto> getAdsComments(@PathVariable int ad_pk) {

        Collection<AdsComment> list = adsService.getAdsComments(ad_pk);

        return ResponseWrapper.of(commentMapper.toDto(list));
    }

    @Operation(summary = "addAdsComments", description = "addAdsComments")
    @PostMapping("/{ad_pk}/comments")
    public AdsCommentDto addAdsComments(@PathVariable long ad_pk, @RequestBody AdsCommentDto adsCommentDto) {

        AdsComment adsComment = adsService.addAdsComment(ad_pk, commentMapper.toEntity(adsCommentDto));

        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "deleteAdsComment", description = "deleteAdsComment")
    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable int ad_pk, @PathVariable long id,
                                                       Authentication authentication) {

        if (adsService.deleteAdsComment(ad_pk, id, authentication)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "getAdsComment", description = "getAdsComment")
    @GetMapping("/{ad_pk}/comment/{id}")
    public AdsCommentDto getAdsComment(@PathVariable int ad_pk, @PathVariable long id) {

        AdsComment adsComment = adsService.getAdsComment(ad_pk, id);

        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "updateAdsComment", description = "updateAdsComment")
    @PatchMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable int ad_pk, @PathVariable long id,
                                                          @RequestBody AdsCommentDto updatedAdsCommentDto,
                                                          Authentication authentication) {

        AdsCommentDto updateAdsCommentDto = commentMapper.toDto(adsService.updateAdsComment(ad_pk, id,
                commentMapper.toEntity(updatedAdsCommentDto), authentication));


        if (updateAdsCommentDto.equals(updatedAdsCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(updateAdsCommentDto);
    }
}
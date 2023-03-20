package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private final AdsMapper mapper;

    private final AdsCommentMapper commentMapper;

    private final AdsService adsService;

    private final ImageService imagesService;

    @Operation(summary = "getAllAds", description = "getAllAds")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {

        Collection<Ads> listAds = adsService.getAllAds();

        return ResponseWrapper.of(mapper.toDto(listAds));
    }

    @SneakyThrows
    @ExceptionHandler
    @Operation(summary = "addAds", description = "addAds")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "Данные нового объявления")
                                         @RequestPart("image") MultipartFile image,
                                         @Valid @RequestPart("properties") CreateAdsDto dto) {

        Ads ads = adsService.createAds(mapper.toEntity(dto));

        ads.setImage(imagesService.uploadImage(image));

        return ResponseEntity.ok(mapper.toDto(adsService.createAds(ads)));
    }

    @GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {

        Image image = imagesService.getImageById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(image.getImage());
    }

    @Operation(summary = "getAdsMe", description = "getAdsMe")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {

        Collection<Ads> listAds = adsService.getAdsMe();

        return ResponseWrapper.of(mapper.toDto(listAds));
    }

    @Operation(summary = "removeAds", description = "removeAds")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable("id") long id) {

        adsService.removeAdsById(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getAds", description = "getAds")
    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable("id") long id) {

        return mapper.toFullAdsDto(adsService.getAdsById(id));
    }

    @SneakyThrows
    @Operation(summary = "updateAdsImage", description = "updateAdsImage")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable("id") long id,
                                                 @Parameter(description = "Новая картинка")
                                                 @RequestPart(value = "image") @Valid MultipartFile image) {

        Ads ads = adsService.getAdsById(id);

        Ads updatedAds = adsService.updateAdsImage(ads, imagesService.uploadImage(image));

        return ResponseEntity.ok(mapper.toDto(updatedAds));
    }

    @SneakyThrows
    @Operation(summary = "updateAds", description = "updateAds")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("id") long id,
                                            @Valid @RequestBody AdsDto updatedAdsDto) {

        Ads ads = mapper.toEntity(updatedAdsDto);

        ads.setId(id);

        AdsDto updateAdsDto = mapper.toDto(adsService.updateAds(ads));

        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "getAdsComments", description = "getAdsComments")
    @GetMapping("/{adKey}/comments")
    public ResponseWrapper<AdsCommentDto> getAdsComments(@PathVariable("adKey") int adKey) {

        Collection<AdsComment> list = adsService.getAdsComments(adKey);

        return ResponseWrapper.of(commentMapper.toDto(list));
    }

    @Operation(summary = "addAdsComments", description = "addAdsComments")
    @PostMapping("/{adKey}/comments")
    public AdsCommentDto addAdsComments(@PathVariable("adKey") long adKey,
                                        @Valid @RequestBody AdsCommentDto adsCommentDto) {

        AdsComment adsComment = adsService.addAdsComment(adKey, commentMapper.toEntity(adsCommentDto));

        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "deleteAdsComment", description = "deleteAdsComment")
    @DeleteMapping("/{adKey}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable("adKey") int adKey, @PathVariable("id") long id) {

        adsService.deleteAdsComment(adKey, id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getAdsComment", description = "getAdsComment")
    @GetMapping("/{adKey}/comments/{id}")
    public AdsCommentDto getAdsComment(@PathVariable("adKey") int adKey, @PathVariable("id") long id) {

        AdsComment adsComment = adsService.getAdsComment(adKey, id);

        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "updateAdsComment", description = "updateAdsComment")
    @PatchMapping("/{adKey}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable("adKey") int adKey,
                                                          @PathVariable("id") long id,
                                                          @Valid @RequestBody AdsCommentDto updatedAdsCommentDto) {

        AdsComment adsComment = commentMapper.toEntity(updatedAdsCommentDto);

        AdsCommentDto updateAdsCommentDto = commentMapper.toDto(adsService.updateAdsComment(adKey, id, adsComment));

        return ResponseEntity.ok(updateAdsCommentDto);
    }
}
package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Images;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImagesService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.Collection;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@EnableMethodSecurity
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private final AdsMapper mapper;

    private final AdsCommentMapper commentMapper;

    private final AdsService adsService;

    private final ImagesService imagesService;

    @Operation(summary = "getAllAds", description = "getAllAds")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        Collection<Ads> listAds = adsService.getAllAds();
        return ResponseWrapper.of(mapper.toDto(listAds));
    }

    @SneakyThrows
    @Operation(summary = "addAds", description = "addAds")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") @Valid CreateAdsDto dto) {
        Ads ads = adsService.createAds(mapper.toEntity(dto));
        Images images = imagesService.uploadImage(image, ads);
        ads.setImage(images);
        return ResponseEntity.ok(mapper.toDto(adsService.createAds(ads)));
    }

    @GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Images images = imagesService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(images.getMediaType()));
        headers.setContentLength(images.getImage().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(images.getImage());
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
        return mapper.toFullAdsDto(adsService.getAds(id));
    }

    @SneakyThrows
    @Operation(summary = "updateAdsImage", description = "updateAdsImage")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable long id, Authentication authentication,
                                                 @Parameter(in = ParameterIn.DEFAULT, description = "Новая картинка",
                                                         schema = @Schema())
                                                 @RequestPart(value = "image") @Valid MultipartFile image) {

        Ads ads = adsService.getAds(id);

        long adsOldImageId = ads.getImage().getId();

        Images images = imagesService.uploadImage(image, ads);

        ads.setImage(images);

        Ads updatedAds = adsService.updateAdsImage(ads, authentication, images);

        imagesService.removeImage(adsOldImageId);

        if (!ads.equals(updatedAds)) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
        }

        return ResponseEntity.ok(mapper.toDto(updatedAds));
    }

    @SneakyThrows
    @Operation(summary = "updateAds", description = "updateAds")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, Authentication authentication,
                                            @RequestBody AdsDto updatedAdsDto) {

        AdsDto updateAdsDto = mapper.toDto(adsService.updateAds(id, mapper.toEntity(updatedAdsDto), authentication));

        if (updateAdsDto.equals(updatedAdsDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "getAdsComments", description = "getAdsComments")
    @GetMapping("/{adKey}/comments")
    public ResponseWrapper<AdsCommentDto> getAdsComments(@PathVariable int adKey) {
        Collection<AdsComment> list = adsService.getAdsComments(adKey);
        return ResponseWrapper.of(commentMapper.toDto(list));
    }

    @Operation(summary = "addAdsComments", description = "addAdsComments")
    @PostMapping("/{adKey}/comments")
    public AdsCommentDto addAdsComments(@PathVariable long adKey, @RequestBody AdsCommentDto adsCommentDto) {
        AdsComment adsComment = adsService.addAdsComment(adKey, commentMapper.toEntity(adsCommentDto));
        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "deleteAdsComment", description = "deleteAdsComment")
    @DeleteMapping("/{adKey}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable int adKey, @PathVariable long id,
                                                       Authentication authentication) {
        if (adsService.deleteAdsComment(adKey, id, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "getAdsComment", description = "getAdsComment")
    @GetMapping("/{adKey}/comments/{id}")
    public AdsCommentDto getAdsComment(@PathVariable int adKey, @PathVariable long id) {
        AdsComment adsComment = adsService.getAdsComment(adKey, id);
        return commentMapper.toDto(adsComment);
    }

    @Operation(summary = "updateAdsComment", description = "updateAdsComment")
    @PatchMapping("/{adKey}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable int adKey, @PathVariable long id,
                                                          @RequestBody AdsCommentDto updatedAdsCommentDto,
                                                          Authentication authentication) {
        AdsCommentDto updateAdsCommentDto = commentMapper.toDto(adsService.updateAdsComment(adKey, id,
                commentMapper.toEntity(updatedAdsCommentDto), authentication));
        if (updateAdsCommentDto.equals(updatedAdsCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(updateAdsCommentDto);
    }
}
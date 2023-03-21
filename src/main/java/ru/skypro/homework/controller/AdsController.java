package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private final AdsService adsService;

    private final ImageService imagesService;

    @Operation(summary = "getAllAds", description = "getAllAds")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        return ResponseWrapper.of(adsService.getAllAds());
    }

    @SneakyThrows
    @ExceptionHandler
    @Operation(summary = "addAds", description = "addAds")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") @Valid CreateAdsDto dto) {
        return ResponseEntity.ok(adsService.createAds(dto, image));
    }

    @GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(imagesService.getImageBytesArray(id));
    }

    @Operation(summary = "getAdsMe", description = "getAdsMe")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {
        return ResponseWrapper.of(adsService.getAdsMe());
    }

    @SneakyThrows
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
        return adsService.getFullAdsDto(id);
    }

    @SneakyThrows
    @Operation(summary = "updateAdsImage", description = "updateAdsImage")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable long id, Authentication authentication,
                                                 @Parameter(in = ParameterIn.DEFAULT, description = "Новая картинка",
                                                         schema = @Schema())
                                                 @RequestPart(value = "image") @Valid MultipartFile image) {
        return ResponseEntity.ok(imagesService.updateImage(image, authentication, id));
    }

    @SneakyThrows
    @Operation(summary = "updateAds", description = "updateAds")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, Authentication authentication,
                                            @RequestBody AdsDto updatedAdsDto) {
        AdsDto updateAdsDto = adsService.updateAds(id, updatedAdsDto, authentication);
        if (updateAdsDto.equals(updatedAdsDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "getAdsComments", description = "getAdsComments")
    @GetMapping("/{adKey}/comments")
    public ResponseWrapper<AdsCommentDto> getAdsComments(@PathVariable int adKey) {
        return ResponseWrapper.of(adsService.getAdsComments(adKey));
    }

    @Operation(summary = "addAdsComments", description = "addAdsComments")
    @PostMapping("/{adKey}/comments")
    public AdsCommentDto addAdsComments(@PathVariable long adKey, @RequestBody AdsCommentDto adsCommentDto) {
        return adsService.addAdsComment(adKey, adsCommentDto);
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
        return adsService.getAdsComment(adKey, id);
    }

    @Operation(summary = "updateAdsComment", description = "updateAdsComment")
    @PatchMapping("/{adKey}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable int adKey, @PathVariable long id,
                                                          @RequestBody AdsCommentDto updateAdsCommentDto,
                                                          Authentication authentication) {
        AdsCommentDto updatedAdsCommentDto = adsService.updateAdsComment(adKey, id,
                updateAdsCommentDto, authentication);
        if (updateAdsCommentDto.equals(updatedAdsCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updatedAdsCommentDto);
    }
}
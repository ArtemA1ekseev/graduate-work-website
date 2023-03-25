package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
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

    Logger logger = LoggerFactory.getLogger(AdsController.class);


    private final AdsService adsService;

    private final ImageService imagesService;

    @Operation(summary = "Просмотр всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            },
            tags = "Ads"
    )
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {
        logger.info("Request for get all ads");
        return ResponseWrapper.of(adsService.getAllAds());
    }

    @SneakyThrows
    @ExceptionHandler
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(summary = "Создание объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(in = ParameterIn.DEFAULT, description = "Данные нового объявления",
            required = true, schema = @Schema())
                                         @RequestPart("image") MultipartFile image,
                                         @RequestPart("properties") @Valid CreateAdsDto dto) {
        logger.info("Request for add new ad");
        return ResponseEntity.ok(adsService.createAds(dto, image));
    }

    @Operation(summary = "Просмотр изображения к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Изображение, найденное по id",
                            content = @Content(
                                    mediaType = MediaType.IMAGE_PNG_VALUE,
                                    schema = @Schema(implementation = Image.class)
                            )
                    )
            },
            tags = "Image"
    )@GetMapping(value = "images/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        logger.info("Request for get image by id");
        return ResponseEntity.ok(imagesService.getImageBytesArray(id));
    }

    @Operation(summary = "Просмотр всех моих объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все мои объявления",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto[].class)
                            )
                    )
            },
            tags = "Ads"
    )
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {
        logger.info("Request for get my ads");
        return ResponseWrapper.of(adsService.getAdsMe());
    }

    @SneakyThrows
    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeAds(@PathVariable long id, Authentication authentication) {
        logger.info("Request for delete ad by id");
        if (adsService.removeAds(id, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "Поиск объявления по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Объявление, найденное по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Ads.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @GetMapping("/{id}")
    public FullAdsDto getAds(@PathVariable long id) {
        logger.info("Request for get ad by id");
        return adsService.getFullAdsDto(id);
    }

    @SneakyThrows
    @Operation(summary = "Загрузка навого изображения к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новое изображение",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            },
            tags = "Image"
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable long id, Authentication authentication, @Parameter(in = ParameterIn.DEFAULT, description = "Загрузите сюда новое изображение",
                                                         schema = @Schema())
                                                 @RequestPart(value = "image") @Valid MultipartFile image) {
        logger.info("Request for update ad image by id");
        return ResponseEntity.ok(imagesService.updateImage(image, authentication, id));
    }

    @SneakyThrows
    @Operation(summary = "Изменение объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененное объявление",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class)
                            )
                    )
            },
            tags = "Ads"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, Authentication authentication,
                                            @RequestBody AdsDto updatedAdsDto) {
        logger.info("Request for update ad by id");
        AdsDto updateAdsDto = adsService.updateAds(id, updatedAdsDto, authentication);
        if (updateAdsDto.equals(updatedAdsDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updateAdsDto);
    }

    @Operation(summary = "Просмотр комментариев к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарии",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsCommentDto[].class)
                            )
                    )
            },
            tags = "Comments"
    )
    @GetMapping("/{adKey}/comments")
    public ResponseWrapper<AdsCommentDto> getAdsComments(@PathVariable int adKey) {
        logger.info("Request for get ad comment");
        return ResponseWrapper.of(adsService.getAdsComments(adKey));
    }

    @Operation(summary = "Написать комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsCommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @PostMapping("/{adKey}/comments")
    public AdsCommentDto addAdsComments(@PathVariable long adKey, @RequestBody AdsCommentDto adsCommentDto) {
        logger.info("Request for add ad comment");
        return adsService.addAdsComment(adKey, adsCommentDto);
    }

    @Operation(summary = "Удаление комментариев",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsCommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @DeleteMapping("/{adKey}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable int adKey, @PathVariable long id,
                                                       Authentication authentication) {
        logger.info("Request for delete ad comment");
        if (adsService.deleteAdsComment(adKey, id, authentication)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
    }

    @Operation(summary = "Поиск комментария по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsCommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @GetMapping("/{adKey}/comments/{id}")
    public AdsCommentDto getAdsComment(@PathVariable int adKey, @PathVariable long id) {
        logger.info("Request for get ad comment");
        return adsService.getAdsComment(adKey, id);
    }

    @Operation(summary = "Изменение комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный комментарий",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsCommentDto.class)
                            )
                    )
            },
            tags = "Comments"
    )
    @PatchMapping("/{adKey}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable int adKey, @PathVariable long id,
                                                          @RequestBody AdsCommentDto updateAdsCommentDto,
                                                          Authentication authentication) {
        logger.info("Request for update ad comment");
        AdsCommentDto updatedAdsCommentDto = adsService.updateAdsComment(adKey, id,
                updateAdsCommentDto, authentication);
        if (updateAdsCommentDto.equals(updatedAdsCommentDto)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(updatedAdsCommentDto);
    }
}
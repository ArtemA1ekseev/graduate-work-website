package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;

@CrossOrigin(value = {"http://localhost:3000", "http://adc:3000"})
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;

    private final CommentService commentService;

    public AdsController(AdsService adsService, CommentService commentService) {
        this.adsService = adsService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@RequestParam MultipartFile image, CreateAds ads) {
        return ResponseEntity.ok(adsService.createAds(image, ads));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @GetMapping(params = {"search"})
    public ResponseEntity<ResponseWrapperAds> findAds(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(adsService.findAds(search));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        adsService.removeAds(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @PatchMapping("{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id, @RequestBody CreateAds createAds) {
        return ResponseEntity.ok(adsService.updateAdvert(id, createAds));
    }

    @GetMapping("{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable int ad_pk) {
        return ResponseEntity.ok(commentService.getAdsAllComments(ad_pk));
    }

    @PostMapping("{ad_pk}/comment")
    public ResponseEntity<AdsCommentDto> addAdsComment(@PathVariable int ad_pk, @RequestBody AdsCommentDto adsCommentDto) {
        return ResponseEntity.ok(commentService.createComment(ad_pk, adsCommentDto));
    }

    @DeleteMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<Void> deleteAdsComment(@PathVariable int ad_pk,
                                                 @PathVariable int id) {
        commentService.deleteAdsComment(ad_pk, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable int ad_pk,
                                                       @PathVariable int id) {
        return ResponseEntity.ok(commentService.getAdsComment(ad_pk, id));
    }


    @PatchMapping("{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable int ad_pk,
                                                          @PathVariable int id,
                                                          @RequestBody AdsCommentDto comment) {
        return ResponseEntity.ok(commentService.updateAdsComment(ad_pk, id, comment));
    }
}
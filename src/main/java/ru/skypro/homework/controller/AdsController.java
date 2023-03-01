package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    @GetMapping
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @PostMapping
    public ResponseEntity<AdsDto> addAds(@RequestBody CreateAds createAds) {
        return ResponseEntity.ok(new AdsDto());
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(@RequestParam(value = "authenticated", required = false) Boolean authenticated,
                                                       @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority,
                                                       @RequestParam(value = "credentials", required = false) Object credentials,
                                                       @RequestParam(value = "details", required = false) Object details,
                                                       @RequestParam(value = "principal", required = false) Object principal) {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @GetMapping("/{ad_pk}/comment")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable("ad_pk") String adPk) {
        return ResponseEntity.ok(new ResponseWrapperAdsComment());
    }

    @PostMapping("/{ad_pk}/comment")
    public ResponseEntity<AdsCommentDto> addAdsComment(@PathVariable("ad_pk") String adPk, @RequestBody AdsCommentDto adsCommentDto) {
        return ResponseEntity.ok(adsCommentDto);
    }

    @DeleteMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> deleteAdsComment(@PathVariable("ad_pk") String adPk,
                                                          @PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable("ad_pk") String adPk,
                                                       @PathVariable int id) {
        return ResponseEntity.ok(new AdsCommentDto());
    }


    @PatchMapping("/{ad_pk}/comment/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable("ad_pk") String adPk,
                                                          @PathVariable int id,
                                                          @RequestBody AdsCommentDto adsCommentDto) {
        return ResponseEntity.ok(adsCommentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdsDto> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new FullAds());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id, @RequestBody AdsDto adsDto) {
        return ResponseEntity.ok(adsDto);
    }
}

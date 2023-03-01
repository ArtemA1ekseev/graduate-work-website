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
    public ResponseEntity<Ads> addAds(@RequestBody CreateAds createAds) {
        return ResponseEntity.ok(new Ads());
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(@RequestParam(value = "authenticated", required = false) Boolean authenticated,
                                                       @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority,
                                                       @RequestParam(value = "credentials", required = false) Object credentials,
                                                       @RequestParam(value = "details", required = false) Object details,
                                                       @RequestParam(value = "principal", required = false) Object principal) {
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperAdsComment> getAdsComments(@PathVariable("ad_pk") String adPk) {
        return ResponseEntity.ok(new ResponseWrapperAdsComment());
    }

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<Comment> addAdsComment(@PathVariable("ad_pk") String adPk, @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> deleteAdsComment(@PathVariable("ad_pk") String adPk,
                                                    @PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> getAdsComment(@PathVariable("ad_pk") String adPk,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(new Comment());
    }


    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> updateAdsComment(@PathVariable("ad_pk") String adPk,
                                                    @PathVariable int id,
                                                    @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ads> removeAds(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getAds(@PathVariable int id) {
        return ResponseEntity.ok(new FullAds());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }
}

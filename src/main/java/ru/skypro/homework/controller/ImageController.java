package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ads;

@RestController
@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    @PatchMapping("/{id}")
    public ResponseEntity<Ads> updateAdsImage(@PathVariable int id, @RequestBody Ads ads) {
        return ResponseEntity.ok(ads);
    }
}

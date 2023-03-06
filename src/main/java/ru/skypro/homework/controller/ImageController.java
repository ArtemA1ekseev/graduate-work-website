package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;

@RestController
@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAdsImage(@PathVariable int id, @RequestBody AdsDto adsDto) {
        return ResponseEntity.ok(adsDto);
    }
}

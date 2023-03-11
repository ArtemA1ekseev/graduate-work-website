package ru.skypro.homework.entity;

import lombok.Data;
import javax.persistence.*;
/**
 * Class of AdsImage (advertisement image/изображение в объявлениях)
 */
@Data
@Entity
public class AdsImage {

    /** "pk/id изображения" field */
    @Id
    @GeneratedValue
    private int pk;
    /** "size" field */
    private Long size;
    /** "path" field */
    private String path;
    /** "mediaType" field */
    private String mediaType;
    /** "data" field */
    private byte[] data;
    /** "ads/объявление" field */
    @ManyToOne
    private Ads ads;

//    public AdsImage() {
//    }
//
//    public int getPk() {
//        return pk;
//    }
//
//    public Long getSize() {
//        return size;
//    }
//
//    public void setSize(Long size) {
//        this.size = size;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getMediaType() {
//        return mediaType;
//    }
//
//    public void setMediaType(String mediaType) {
//        this.mediaType = mediaType;
//    }
//
//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }
//
//    public Ads getAds() {
//        return ads;
//    }
//
//    public void setAds(Ads ads) {
//        this.ads = ads;
//    }
}

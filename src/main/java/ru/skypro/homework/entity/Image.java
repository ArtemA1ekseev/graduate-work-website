package ru.skypro.homework.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Class of Image (advertisement image/изображение в объявлениях).
 */
@Entity
@Table(name = "image")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Image {
    /**
     * "id изображения" field
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Lob
    @Type(type = "binary")
    private byte[] image;
    /**
     * "fileSize/размер файла
     */
    private long fileSize;
    /**
     * "mediaType/тип данных
     */
    private String mediaType;
    /**
     * "filePath/путь к файлу" field
     */
    private String filePath;
    /**
     * "ads/объявление" field
     */
    @OneToOne
    private Ads ads;

    public String toString() {
        return "AdsEntity(id=" + this.getId() + ", image=" + java.util.Arrays.toString(this.getImage()) + ")";
    }
}
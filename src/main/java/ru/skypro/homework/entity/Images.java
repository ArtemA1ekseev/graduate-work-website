package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "images")
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Images {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Lob
    private byte[] image;

    private String filePath;

    private long fileSize;

    @OneToOne
    private Ads ads;

    public String toString() {
        return "AdsEntity(id=" + this.getId() + ", image=" + java.util.Arrays.toString(this.getImage()) + ")";
    }
}
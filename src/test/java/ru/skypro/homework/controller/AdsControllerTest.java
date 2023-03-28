package ru.skypro.homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdsController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AdsControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private AdsServiceImpl adsService;

    @MockBean
    private ImageService imagesService;


    @Autowired
    AdsControllerTest(WebApplicationContext webApplicationContext, ObjectMapper objectMapper) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = objectMapper;
    }

    @Test
    void getAllAds() throws Exception {
        User user1 = new User(1, "a@mail.ru", "123", "Amt", "Lom",
                "+79123456789", Role.USER);

        User user2 = new User(2, "b@mail.ru", "321", "Tma", "Mol",
                "+79123456790", Role.USER);

        byte[] imgStub = new byte[]{1, 0, 1};
        byte[] imgStub1 = new byte[]{0, 1, 0};
        Image image = new Image();
        Image image1 = new Image();
        image.setImage(imgStub);
        image1.setImage(imgStub1);

        Ads ads1 = new Ads(1, user1, 123, "box", "good", image);
        Ads ads2 = new Ads(2, user2, 321, "xob", "doog", image1);

        Collection<Ads> adss = new ArrayList<>();
        adss.add(ads1);
        adss.add(ads2);

        List<AdsDto> adssDto = new ArrayList<>();

        AdsDto adsDto1 = new AdsDto();
        adsDto1.setDescription("good");
        adsDto1.setAuthor(1);
        adsDto1.setImage("image");
        adsDto1.setTitle("box");
        adsDto1.setPrice(123);

        AdsDto adsDto2 = new AdsDto();
        adsDto2.setDescription("doog");
        adsDto2.setAuthor(2);
        adsDto2.setImage("image1");
        adsDto2.setTitle("xob");
        adsDto2.setPrice(321);

        adssDto.add(adsDto1);
        adssDto.add(adsDto2);

        when(adsService.getAllAds()).thenReturn(adssDto);

        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results[0]").value(adssDto.get(0)))
                .andExpect(jsonPath("$.results[1].title").value(adsDto2.getTitle()))
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void addAds() throws Exception {
        byte[] imgStub = new byte[]{1, 0, 1};
        MockPart partFile = new MockPart("image", "image", imgStub);
        MockMultipartFile mockImage = new MockMultipartFile("image", imgStub);

        CreateAdsDto createAdsDto = new CreateAdsDto();
        createAdsDto.setTitle("Название");
        createAdsDto.setDescription("Описание");

        AdsDto adsDto = new AdsDto();
        adsDto.setTitle("Название");
        adsDto.setDescription("Описание");

        when(adsService.createAds(any(CreateAdsDto.class), any(MultipartFile.class))).thenReturn(adsDto);

        mockMvc.perform(multipart("/ads")
                        .file(mockImage)
                        .part(new MockPart("properties", objectMapper.writeValueAsBytes(createAdsDto))))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getAdsMe() throws Exception {
        Collection<Ads> adsList = new ArrayList<>();

        Ads ads1 = new Ads();
        Ads ads2 = new Ads();

        adsList.add(ads1);
        adsList.add(ads2);

        List<AdsDto> adsDtoList = new ArrayList<>();

        AdsDto adsDto1 = new AdsDto();
        AdsDto adsDto2 = new AdsDto();

        adsDto1.setTitle("Название");
        adsDto2.setDescription("Описание");


        adsDtoList.add(adsDto1);
        adsDtoList.add(adsDto2);

        when(adsService.getAdsMe()).thenReturn(adsDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results[0].title").value(adsDtoList.get(0).getTitle()))
                .andExpect(jsonPath("$.results[1].description").value(adsDtoList.get(1).getDescription()));
    }

    @Test
    void removeAds() throws Exception {

        AdsDto adsDto = new AdsDto();
        when(adsService.removeAds(anyLong(), any())).thenReturn(true);

        mockMvc.perform(delete("/ads/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAds() throws Exception {
        FullAdsDto fullAdsDto = new FullAdsDto();

        fullAdsDto.setEmail("d@mail.ru");

        when(adsService.getFullAdsDto(anyLong())).thenReturn(fullAdsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("d@mail.ru"));

        verify(adsService, times(1)).getFullAdsDto(anyLong());
    }

    @Test
    void updateAdsImage() throws Exception {
        AdsDto adsDto = new AdsDto();

        when(imagesService.updateImage(any(MultipartFile.class), any(Authentication.class), anyLong())).thenReturn(adsDto);

        byte[] imgStub = new byte[]{1, 0, 1};

        MockPart partFile = new MockPart("image", "image", imgStub);

        mockMvc.perform(patch("/ads/1/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.addPart(partFile);
                            return request;
                        }))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateAds() throws Exception {
        AdsDto updateAdsDto = new AdsDto();
        AdsDto adsDto = new AdsDto();
        adsDto.setDescription("Описание");
        adsDto.setTitle("Название");

        when(adsService.updateAds(anyLong(), any(), any())).thenReturn(updateAdsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adsDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(adsService, times(1)).updateAds(anyLong(), any(), any());
    }

    @Test
    void getAdsComments() throws Exception {
        List<AdsCommentDto> adsCommentsDto = new ArrayList<>();

        AdsCommentDto adsDtoComment1 = new AdsCommentDto();
        AdsCommentDto adsDtoComment2 = new AdsCommentDto();

        adsCommentsDto.add(adsDtoComment1);
        adsCommentsDto.add(adsDtoComment2);


        when(adsService.getAdsComments(anyLong())).thenReturn(adsCommentsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/1/comments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results[0]").value(adsCommentsDto.get(0)))
                .andExpect(jsonPath("$.results[1]").value(adsCommentsDto.get(1)));
    }

    @Test
    void addAdsComments() throws Exception {
        AdsCommentDto adsCommentDto = new AdsCommentDto();
        adsCommentDto.setText("Комментарий");

        when(adsService.addAdsComment(anyLong(), any(AdsCommentDto.class))).thenReturn(adsCommentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/ads/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adsCommentDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteAdsComment() throws Exception {

        when(adsService.deleteAdsComment(anyLong(), anyLong(), any())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/1/comments/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void getAdsComment() throws Exception {
        AdsCommentDto adsCommentDto = new AdsCommentDto();

        when(adsService.getAdsComment(anyLong(), anyLong())).thenReturn(adsCommentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/1/comments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(adsCommentDto));

        verify(adsService, times(1)).getAdsComment(anyLong(), anyLong());
    }

    @Test
    void updateAdsComment() throws Exception {
        AdsCommentDto adsCommentDto = new AdsCommentDto();
        AdsCommentDto adsCommentDto2 = new AdsCommentDto();
        adsCommentDto2.setPk(3);
        adsCommentDto.setText("Комментарий");
        adsCommentDto2.setText("Комментарий");


        when(adsService.updateAdsComment(anyInt(), anyLong(), any(AdsCommentDto.class), any())).thenReturn(adsCommentDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adsCommentDto2))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
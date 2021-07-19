package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.channel.play.controller.ChannelPlayController;
import com.soccer.web.channel.play.vo.ChannelPlayVO;



@SpringBootTest
@AutoConfigureMockMvc
public class TestChannelPlayController {
	
	@Autowired
	ChannelPlayController channelPlayController;
	
	@Autowired
	MockMvc mvc;
	
	
	@Test
	@Transactional
	@DisplayName("파일업로드")
	void insertChannelPlayTest() throws Exception{

		ChannelPlayVO channelPlayVO = new ChannelPlayVO();
		
		channelPlayVO.setChannelIdx(2);
		channelPlayVO.setChannelPlayIdx(1);
		
		UrlEncodedFormEntity fileUp = new UrlEncodedFormEntity(Arrays.asList(
			new BasicNameValuePair("channelIdx", String.valueOf(channelPlayVO.getChannelIdx())),
			new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayVO.getChannelPlayIdx()))
			
		), "utf-8");
		
		
		FileInputStream fis = new FileInputStream(new File("C:/Users/lsk/asdf.mp4"));
		
		MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "asdf.mp4", "video/mp4", fis);
		

		mvc.perform(MockMvcRequestBuilders.multipart("/channel/play/"+channelPlayVO.getChannelIdx())
			.file(multipartFile)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.content(EntityUtils.toString(fileUp)))
			.andExpect(model().attributeDoesNotExist("code"))
			.andDo(print());
	}
	
	
	@Test
	@Transactional
	@DisplayName("스트리밍")
	void selectChannelPlayDetailTest() throws Exception{
		ChannelPlayVO channelPlayVO = new ChannelPlayVO();
		
		channelPlayVO.setChannelIdx(2);
		channelPlayVO.setChannelPlayIdx(1);
		channelPlayVO.setChannelPlayTitle("asdf.mp4");
		channelPlayVO.setChannelPlayVideo(Paths.get("C:", "downloads", "upload").toString());
		
		UrlEncodedFormEntity fileStream = new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("channelIdx", String.valueOf(channelPlayVO.getChannelIdx())),
				new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayVO.getChannelPlayIdx())),
				new BasicNameValuePair("channelPlayTitle", channelPlayVO.getChannelPlayTitle()),
				new BasicNameValuePair("channelPlayVideo", channelPlayVO.getChannelPlayVideo())
				), "utf-8");
		
		mvc.perform(get("/channel/play/"+channelPlayVO.getChannelIdx()+"/"+channelPlayVO.getChannelPlayIdx())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(fileStream)))
				.andExpect(model().attribute("message", "영상이 재생됩니다."))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
		
		
	}

}

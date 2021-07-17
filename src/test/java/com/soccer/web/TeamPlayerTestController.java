package com.soccer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.soccer.web.channel.play.controller.TeamPlayerController;
import com.soccer.web.channel.play.service.TeamPlayerService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamPlayerTestController {

	@Autowired
	TeamPlayerController teamPlayerController;
	
	@Autowired
	TeamPlayerService teamPlayerService;
	
	@Autowired
	MockMvc mockMvc;
	
	
	
}

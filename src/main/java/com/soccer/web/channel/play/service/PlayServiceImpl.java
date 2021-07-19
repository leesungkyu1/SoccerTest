package com.soccer.web.channel.play.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.play.dao.PlayMapper;

@Service("playService")
public class PlayServiceImpl implements PlayService{

	@Autowired
	private PlayMapper playdao;
	
	@Override
	public void insertVideo(Map<String, String> fileInfo) {
		playdao.insertVideo(fileInfo);
		System.out.println(fileInfo);
	}
}

package com.soccer.web.channel.play.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("playdao")
public interface PlayMapper {

	void insertVideo(Map<String, String> fileInfo);

}

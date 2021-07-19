package com.soccer.web.region.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.region.vo.RegionVO;

@Mapper
public interface RegionMapper {
	
	//지역 목록 가져오기 (행정 구역)
	List<RegionVO> regionList() throws Exception;

}

package com.soccer.web.region.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.region.dao.RegionMapper;
import com.soccer.web.region.vo.RegionVO;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	RegionMapper regionDAO;
	
	//지역 목록 가져오기 (행정 구역)
	@Override
	public List<RegionVO> regionList () throws Exception{
		return regionDAO.regionList();
	}
}

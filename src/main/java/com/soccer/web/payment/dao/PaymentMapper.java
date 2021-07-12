package com.soccer.web.payment.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
	
	//결제
	void payment(Integer userIdx) throws Exception;

	//중복결제 방지
	int payCheck(Integer userIdx) throws Exception;

}

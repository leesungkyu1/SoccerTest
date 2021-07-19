package com.soccer.web.payment.dao;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.payment.vo.PaymentVO;

@Mapper
public interface PaymentMapper {
	
	//결제
	void payment(Integer userIdx) throws Exception;

	//중복결제 방지
	PaymentVO payCheck(Integer userIdx) throws Exception;

	//채널 생성 횟수 차감
	void paymentUpdate(ChannelVO channelVO) throws Exception;

}

package com.soccer.web.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.payment.dao.PaymentMapper;
import com.soccer.web.payment.vo.PaymentVO;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentMapper paymentDAO;
	
	//결제
	@Override
	public void payment(Integer userIdx) throws Exception{
		paymentDAO.payment(userIdx);
	}
	
	//중복결제 방지
	@Override
	public PaymentVO payCheck(Integer userIdx) throws Exception{
		return paymentDAO.payCheck(userIdx);
	}
	
	//결제 생성횟수 차감
	@Override
	public void paymentUpdate(ChannelVO channelVO) throws Exception {
		paymentDAO.paymentUpdate(channelVO);
	}
}

package com.soccer.web.payment.service;

import org.springframework.stereotype.Service;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.payment.vo.PaymentVO;

@Service
public interface PaymentService {

	void payment(Integer userIdx) throws Exception;

	PaymentVO payCheck(Integer userIdx) throws Exception;

	void paymentUpdate(ChannelVO channelVO) throws Exception;

}

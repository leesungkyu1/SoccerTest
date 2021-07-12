package com.soccer.web.payment.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

	void payment(Integer userIdx) throws Exception;

	int payCheck(Integer userIdx) throws Exception;

}

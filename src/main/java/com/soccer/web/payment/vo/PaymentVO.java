package com.soccer.web.payment.vo;

import lombok.Data;

public @Data class PaymentVO {
	private int paymentIdx;
	private int userIdx;
	private Integer paymentCount;
}

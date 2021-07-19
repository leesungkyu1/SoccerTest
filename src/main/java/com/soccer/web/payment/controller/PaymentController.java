package com.soccer.web.payment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.payment.service.PaymentServiceImpl;
import com.soccer.web.payment.vo.PaymentVO;
import com.soccer.web.user.vo.UserVO;

@Controller
public class PaymentController {
	
	@Autowired
	PaymentServiceImpl paymentService;
	
	//결제
	@RequestMapping(value = "/payment" , method = RequestMethod.POST)
	public String payment(HttpSession session, RedirectAttributes attributes) throws Exception{
		UserVO userVO = (UserVO)session.getAttribute("userInfo");
		
		try {
			PaymentVO payCheck = paymentService.payCheck(userVO.getUserIdx());
			
			if(payCheck == null || payCheck.getPaymentCount() == 0) {
				paymentService.payment(userVO.getUserIdx());
				attributes.addAttribute("message", "결제가 완료되었습니다.");								
			}else {
				attributes.addAttribute("message", "중복 결제입니다.");
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 201);
			attributes.addAttribute("message", "결제 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		return "redirect:";
	}
}

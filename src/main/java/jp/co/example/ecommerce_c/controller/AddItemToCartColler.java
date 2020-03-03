package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
import jp.co.example.ecommerce_c.service.AddItemToCartService;

@Controller
@RequestMapping("/add-item-to-cart")
public class AddItemToCartColler {

	@Autowired
	private AddItemToCartService addItemToCartService;
		
	@RequestMapping("")
	public String addItem(AddItemToCartForm addItemToCartForm, @AuthenticationPrincipal LoginUser loginUser) {
		addItemToCartService.addItem(addItemToCartForm, loginUser.getUser().getId());
		return "redirect:/show-item-in-cart";
	}
}

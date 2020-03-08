package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.service.DeleteItemFromCartService;

/**
 * カートの中から商品を削除するコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
public class DeleteItemFromCartController {
	
	@Autowired
	DeleteItemFromCartService deleteItemFromCartService;
	
	/**
	 * カート内の商品を削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 * @return カート画面
	 */
	@RequestMapping("/delete_item")
	public String deleteItem(Integer orderItemId, @AuthenticationPrincipal LoginUser loginUser) {
		deleteItemFromCartService.deleteItemFromCart(orderItemId, loginUser);
		return "redirect:/show_item_in_cart";
	}

}

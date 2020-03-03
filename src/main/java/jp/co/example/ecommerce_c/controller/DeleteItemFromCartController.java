package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
	public String deleteItem(Integer orderItemId) {
		deleteItemFromCartService.deleteItemFromCart(orderItemId);
		return "/show-item-in-cart";
	}

}

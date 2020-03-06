package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
import jp.co.example.ecommerce_c.service.AddItemToCartService;

/**
 * カートの中身に商品を追加するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/addItemToCart")
public class AddItemToCartColler {

	@Autowired
	private AddItemToCartService addItemToCartService;
		
	/**
	 * カートに商品を追加するメソッド.
	 * 
	 * @param addItemToCartForm リクエストパラメータで送られてくる値
	 * @param loginUser ログイン中のユーザーid
	 * @return ショッピングカート一覧画面
	 */
	@RequestMapping("")
	public String addItem(AddItemToCartForm addItemToCartForm, @AuthenticationPrincipal LoginUser loginUser) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		if(principal instanceof LoginUser) {
			user = ((LoginUser)principal).getUser();
		}else {
			String error = principal.toString(); //あってるかわからない
		}
		System.out.println(user);
		addItemToCartService.addItem(addItemToCartForm, 1); //userIdに変更する
		return "redirect:/show-item-in-cart";
	}
}

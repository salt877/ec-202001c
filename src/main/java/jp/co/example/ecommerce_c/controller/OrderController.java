package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.service.OrderService;

/**
 * 注文情報を操作するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@RequestMapping("/order")
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShowOrderConfirmController showOrderConfirmController;
	
	/**
	 * 注文確定を行う.
	 * 
	 * @param orderForm 注文確認画面から送られたリクエストパラメータ
	 * @param result エラーチェック
	 * @return 注文完了画面へリダイレクト
	 */
	@RequestMapping("")
	public String order(@Validated OrderForm orderForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return showOrderConfirmController.showOrderConfirm(model);
		}
		orderService.order(orderForm);
		return "redirect:/order/toFinished";
	}
	
	/**
	 * 注文完了画面に遷移させる為のメソッド.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/toFinished")
	public String toFinished() {
		return "order_finished";
	}
}

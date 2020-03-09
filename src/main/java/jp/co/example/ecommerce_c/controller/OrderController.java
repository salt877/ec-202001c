package jp.co.example.ecommerce_c.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.service.OrderService;

/**
 * 注文情報を操作するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShowOrderConfirmController showOrderConfirmController;

	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	/**
	 * 注文確定を行う.
	 * 
	 * @param orderForm 注文確認画面から送られたリクエストパラメータ
	 * @param result    エラーチェック
	 * @return 注文完了画面へリダイレクト
	 * @throws ParseException
	 */
	@RequestMapping("/order")
	public String order(@Validated OrderForm orderForm, BindingResult result, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Date nowDt = new Date(); // 現在の日時を取得
		Date deriveryDate = orderForm.getDeliveryDate(); // 配達日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = sdf.format(nowDt);
		String deriveryDateStr = sdf.format(deriveryDate);
		Integer completeTo = nowDateStr.compareTo(deriveryDateStr);
		LocalDateTime nowLDT = LocalDateTime.now();
		Integer nowHour = nowLDT.getHour();
		if (completeTo > 0) {
			result.rejectValue("deliveryDate", null, "過去の日付が選択されています");
		} else if (completeTo == 0 && orderForm.getDeliveryTime() < nowHour) {
			result.rejectValue("deliveryTime", null, "過去の時間が選択されています");
		}
		if (result.hasErrors()) {
			return showOrderConfirmController.showOrderConfirm(model, loginUser);
		}
		orderService.order(orderForm, loginUser);
		orderService.sendMailForOrder();
		return "redirect:/to_order_finished";
	}

	/**
	 * 注文完了画面に遷移させる為のメソッド.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/to_order_finished")
	public String toFinished() {
		return "order_finished";
	}
}
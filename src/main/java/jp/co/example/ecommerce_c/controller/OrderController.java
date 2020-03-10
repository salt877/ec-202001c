package jp.co.example.ecommerce_c.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
		LocalDate nowLD = LocalDate.now();
		LocalDate threeDaysLaterLD = nowLD.plusDays(3);
		LocalDateTime nowLDT = LocalDateTime.now();
		try {
			LocalDate deliveryLD = orderForm.getDeliveryDate().toLocalDate();
			if (deliveryLD.isBefore(nowLD)) {
				result.rejectValue("deliveryDate", null, "※※※過去の日付が選択されています※※※");
			} else if (deliveryLD.isEqual(nowLD) && orderForm.getDeliveryTime() <= nowLDT.getHour()) {
				result.rejectValue("deliveryTime", null, "※※※過去の時間が選択されています※※※");
			} else if (deliveryLD.isAfter(nowLD) && deliveryLD.isAfter(threeDaysLaterLD)) {
				result.rejectValue("deliveryDate", null, "※※※本日から3日以内の日付を選択してください※※※");
			}
		} catch (Exception e) {
			result.rejectValue("deliveryDate", null, "※※※配達日時を選択してください※※※");
		}
		if (result.hasErrors()) {
			return showOrderConfirmController.showOrderConfirm(model, loginUser);
		}
		orderService.order(orderForm, loginUser);
		orderService.sendMailForOrder();
		return "redirect:/to_order_finished";
	}

	public java.util.Date convertToUtilDate(java.sql.Date sqlDate) {
		return sqlDate;
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
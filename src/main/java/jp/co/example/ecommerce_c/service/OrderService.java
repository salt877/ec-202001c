package jp.co.example.ecommerce_c.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.UserRepository;

/**
 * 注文情報に関するサービスクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class OrderService {
	
	@Autowired
	private MailSender sender;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/** オーダーした際に注文情報を格納してメール送信の際に使用する変数 */
	private Order orderInfo = null;
	
	
	/**
	 * 注文確定をする為、ordersテーブルを更新する.
	 * 
	 * @param orderForm 注文確認画面にてリクエストパラメータから送られた値
	 */
	public void order(OrderForm orderForm, @AuthenticationPrincipal LoginUser loginUser) {
		
		//ordersテーブルからログインしているユーザーのid、statusが0(未入金)のデータを取得(1件)
		List<Order> orderList = orderRepository.findByUserIdAndStatusForOrder(loginUser.getUser().getId(), 0);
		Order order = orderList.get(0);
		
		//注文確認画面にて送られたリクエストパラメータの値をorderにセットする
		BeanUtils.copyProperties(orderForm, order);
		//注文日を現在日で取得しセットする
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		//配送日時をセットする
		Timestamp deliveryTime = new Timestamp(orderForm.getDeliveryDate().getTime());
		deliveryTime.setHours(orderForm.getDeliveryTime());
		order.setDeliveryTime(deliveryTime);
		//合計金額をセットする
		Integer totalPrice = 0;
		List<OrderItem> orderItemList = order.getOrderItemList();
		for(OrderItem orderItem : orderItemList) {
			if(orderItem.getSize() == 'M') {
				totalPrice = totalPrice + orderItem.getItem().getPriceM() + orderItem.getSubTotal();
			}else {
				totalPrice = totalPrice + orderItem.getItem().getPriceL() + orderItem.getSubTotal();
			}
		}
		order.setTotalPrice(totalPrice);
		order.setUserId(loginUser.getUser().getId());
		orderInfo = order;
		//ordersテーブルの情報を更新する
		orderRepository.updateOrder(order);
	}	
	
	/**
	 * 注文完了メールを送信するメソッド.
	 * 
	 * @param order 注文情報
	 */
	public void sendMailForOrder() {
		
		User user = userRepository.findById(orderInfo.getUserId()).get(0);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("rakuraku.coffee.202001c@gmail.com");
		msg.setTo(orderInfo.getDestinationEmail());
		msg.setSubject("【RakuRaku Coffee】ご注文の確認");
		msg.setText(
			user.getName() + "様\n"
			+ "\n"
			+ "この度は「RakuRaku Coffee」をご利用いただきまして、誠にありがとうございます。\n"
			+ "お客様のご注文を承りましたのでお知らせいたします。\n"
			+ "\n"
			+ "またのご利用をお待ちしております。\n"
			+ "\n"
			+ "RakuRaku Coffee"
		);
		try {
			this.sender.send(msg);
		}catch(MailException ex){
			System.err.println(ex.getMessage());
		}
	}
}
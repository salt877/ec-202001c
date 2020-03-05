package jp.co.example.ecommerce_c.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.repository.OrderRepository;

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
	
	public void sendMail(Order order) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setFrom("rakuraku.coffee.202001c@gmail.com");
		msg.setTo(order.getDestinationEmail());
		msg.setSubject("【RakuRaku Coffee】ご注文の確認");
		msg.setText(
				order.getUser().getName() + "様\n"
				+ "\n"
				+ "この度は「RakuRaku Coffee」をご利用いただきまして、誠にありがとうございます。\n"
				+ "お客様のご注文を承りましたのでお知らせいたします。"
				);
		this.sender.send(msg);
	}
	
	/**
	 * 注文確定をする為、ordersテーブルを更新する.
	 * 
	 * @param orderForm 注文確認画面にてリクエストパラメータから送られた値
	 */
	public void order(OrderForm orderForm) {
		
		//ordersテーブルからログインしているユーザーのid、statusが0(未入金)のデータを取得(1件)
		List<Order> orderList = orderRepository.findByUserIdAndStatusForOrder(1, 0); //userIdに今後変える
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
		order.setUserId(1); //userIdあとで帰る
		//ordersテーブルの情報を更新する
		orderRepository.updateOrder(order);
	}	
}

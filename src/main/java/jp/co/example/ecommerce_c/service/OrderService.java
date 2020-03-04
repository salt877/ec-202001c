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
	
	public void order(OrderForm orderForm) {
		List<Order> orderList = orderRepository.findByUserIdAndStatusForOrder(1, 0);
		Order order = orderList.get(0);
		BeanUtils.copyProperties(orderForm, order);
		order.setUserId(1);
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		Timestamp deliveryTime = new Timestamp(orderForm.getDeliveryDate().getTime());
		deliveryTime.setHours(orderForm.getDeliveryTime());
		order.setDeliveryTime(deliveryTime);
		Integer totalPrice = 0;
		List<OrderItem> orderItemList = order.getOrderItemList();
		for(OrderItem orderItem : orderItemList) {
			if(orderItem.getSize() == 'M') {
				totalPrice = totalPrice + orderItem.getItem().getPriceM() + orderItem.getSubTotal();
			}else {
				totalPrice = totalPrice + orderItem.getItem().getPriceL() + orderItem.getSubTotal();
			}
		}
		System.out.println(totalPrice);
		order.setTotalPrice(totalPrice);
		orderRepository.updateOrder(order);
	}
}

package jp.co.example.ecommerce_c.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.repository.ItemRepository;
import jp.co.example.ecommerce_c.repository.OrderItemRepository;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.OrderToppingRepository;

/**
 * カートの中から商品を削除するサービスクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class DeleteItemFromCartService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * カートの中から商品を削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 */
	public void deleteItemFromCart(Integer orderItemId, @AuthenticationPrincipal LoginUser loginUser) {
		Integer price = 0;
		OrderItem orderItem = orderItemRepository.findByOrderItemId(orderItemId);
		System.out.println(orderItem);
		if(orderItem.getSize() == 'M') {
			Integer itemId = orderItem.getItemId();
			Integer mPrice = itemRepository.findById(itemId).getPriceM();
			price = mPrice + orderToppingRepository.findByOrderItemId(orderItemId).size() * 200;
		}else {
			Integer itemId = orderItem.getItemId();
			Integer lPrice = itemRepository.findById(itemId).getPriceL();
			price = lPrice + orderToppingRepository.findByOrderItemId(orderItemId).size() * 300;
		}
		price = price * orderItem.getQuantity();
		
		if(loginUser != null) {
			orderRepository.subtractTotalPrice(loginUser.getUser().getId(), price);
		}else {
			Order sessionOrder = (Order) session.getAttribute("sessionOrder");
			orderRepository.subtractTotalPrice(sessionOrder.getUserId(), price);
			Integer totalPrice = sessionOrder.getTotalPrice() - price;
			sessionOrder.setTotalPrice(totalPrice);
			session.setAttribute("sessionOrder", sessionOrder);
		}
		orderItemRepository.deleteItemById(orderItemId);
		orderToppingRepository.deleteItemById(orderItemId);

	}
}

package jp.co.example.ecommerce_c.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.repository.OrderItemRepository;
import jp.co.example.ecommerce_c.repository.OrderRepository;

/**
 * ショッピングカートの情報を操作するサービス
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class ShowItemInCartService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private HttpSession session;

	/**
	 * 注文状態が注文前であり引数のuserIdに一致した注文を取得する
	 * 
	 * @param userId ユーザーID
	 * @return 注文状態が注文前であり引数のuserIdに一致した注文
	 */
	public List<Order> showItemInCart(Integer userId, @AuthenticationPrincipal LoginUser loginUser){
		Integer sessionUserId = 0;
		Order order = new Order();
		List<Order> orderList = new ArrayList<>();
		if(loginUser != null) {
			orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
			if(orderList.size() == 0) {
				order = new Order();
				order.setUserId(userId);
				order.setStatus(0);
				order.setTotalPrice(0);
				orderRepository.insert(order);
				orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
			}
			Integer orderId = orderList.get(0).getId();
			Order sessionOrder = (Order)session.getAttribute("sessionOrder");
			if(sessionOrder != null) {
			Integer sessionOrderId = sessionOrder.getId();
			sessionUserId = sessionOrder.getUserId();
			
			Integer sessionTotalPrice = sessionOrder.getTotalPrice();
			orderRepository.updateTotalPrice(sessionTotalPrice, orderId);
			orderItemRepository.updateOrderId(sessionOrderId, orderId);
			//deleteで消す方法を考える
			orderRepository.deleteByUserId(sessionUserId);
			session.removeAttribute("sessionOrder");
			}
			orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
		}else {
			orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
		}
		return orderList;
	}
}

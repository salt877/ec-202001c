package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.repository.OrderRepository;

/**
 * 注文履歴を表示します.
 * 
 * @author sota_adachi
 *
 */
@Service
public class ShowOrderHistoryService {
	@Autowired
	private OrderRepository orderRepository;

	public List<Order> searchOrderHistoryByUserId(Integer userId) {
		Integer status = 0;
		return orderRepository.findByUserIdAndStatusForOrderHistory(userId, status);
	}
}

package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.repository.OrderRepository;

@Service
@Transactional
public class ShowOrderConfirmService {
	@Autowired
	private OrderRepository orderRepository;

	public List<Order> showInCart(Integer userId, Integer status) {
		return orderRepository.findByUserIdAndStatus(userId, status);
	}
}

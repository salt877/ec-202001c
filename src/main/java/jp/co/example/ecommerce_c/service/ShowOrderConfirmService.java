package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.repository.OrderRepository;

/**
 * 注文内容を表示するサービス.
 * 
 * @author sota_adachi
 *
 */
@Service
@Transactional
public class ShowOrderConfirmService {
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 注文状態が注文前であり引数のuserIdに一致した注文を取得する
	 * 
	 * @param userId ユーザーID
	 * @return 注文状態が注文前であり引数のuserIdに一致した注文
	 */
	public List<Order> showInCart(Integer userId) {
		return orderRepository.findByUserIdAndStatusForOrder(userId, 0);
	}
}

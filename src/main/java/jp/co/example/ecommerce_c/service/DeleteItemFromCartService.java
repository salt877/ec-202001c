package jp.co.example.ecommerce_c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.repository.OrderItemRepository;
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
	
	/**
	 * カートの中から商品を削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 */
	public void deleteItemFromCart(Integer orderItemId) {
		orderItemRepository.deleteItemById(orderItemId);
		orderToppingRepository.deleteItemById(orderItemId);
	}

}

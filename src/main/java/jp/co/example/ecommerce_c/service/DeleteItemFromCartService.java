package jp.co.example.ecommerce_c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	/**
	 * カートの中から商品を削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 */
	public void deleteItemFromCart(Integer orderItemId) {
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
		
		orderRepository.subtractTotalPrice(1, price);
		orderItemRepository.deleteItemById(orderItemId);
		orderToppingRepository.deleteItemById(orderItemId);

	}
}

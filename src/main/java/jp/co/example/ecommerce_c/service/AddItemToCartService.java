package jp.co.example.ecommerce_c.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.OrderTopping;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
import jp.co.example.ecommerce_c.repository.ItemRepository;
import jp.co.example.ecommerce_c.repository.OrderItemRepository;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.OrderToppingRepository;
import jp.co.example.ecommerce_c.repository.ToppingRepository;

@Service
@Transactional
public class AddItemToCartService {

	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	public void addItem(AddItemToCartForm addItemToCartForm, Integer userId) {
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		if(orderList.size() == 0) {
			Order order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			orderRepository.insert(order);
			orderList = orderRepository.findByUserIdAndStatus(userId, 0);
			order = orderList.get(0);
		}
		orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		Order order = orderList.get(0);
		//orderItemの中身を作成(商品id, サイズ, 数量をコピー可能) 注文id, 商品, 注文したトッピングリストを詰めていく
		OrderItem orderItem = new OrderItem();
		BeanUtils.copyProperties(addItemToCartForm, orderItem);
		//注文idをセット
		orderItem.setOrderId(order.getId());
		//商品をセット
		orderItem.setItem(itemRepository.findById(orderItem.getItemId()));
		//orderToppingの中身を作成
		//商品詳細画面でクリックされたチェックボックスのトッピングidをformToppingListに格納する
		List<Integer> formToppingList = addItemToCartForm.getToppingList();
		//orderToppingを複数格納する為のorderToppingListを作成
		List<OrderTopping> orderToppingList = new ArrayList<>();
		OrderItem checkOrderItem = orderItemRepository.save(orderItem);

		//配列を回しリストに詰める
		for(int i = 0; i < formToppingList.size(); i++) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setToppingId(formToppingList.get(i));
			orderTopping.setOrderItemId(orderItem.getId());
			orderTopping.setTopping(toppingRepository.findById(formToppingList.get(i)));
			orderToppingRepository.insert(orderTopping);
			orderToppingList.add(orderTopping);
		}
		orderItem.setList(orderToppingList);
		
		
		Integer itemPrice;	
		
		if(orderItem.getSize() == 'M') {
			itemPrice = orderItem.getItem().getPriceM() + orderItem.getSubTotal();
		}else {
			itemPrice = orderItem.getItem().getPriceL() + orderItem.getSubTotal();
		}
		
		Integer totalPrice = order.getTotalPrice() + itemPrice;
		order.setTotalPrice(totalPrice);
		orderRepository.update(order);
	}
}

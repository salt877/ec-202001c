package jp.co.example.ecommerce_c.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.OrderTopping;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
import jp.co.example.ecommerce_c.repository.ItemRepository;
import jp.co.example.ecommerce_c.repository.OrderItemRepository;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.OrderToppingRepository;
import jp.co.example.ecommerce_c.repository.ToppingRepository;
import jp.co.example.ecommerce_c.repository.UserRepository;

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
		
	@Autowired
	private HttpSession session;
	
	/**
	 * ショッピングカートに商品を追加するメソッド.
	 * 
	 * @param addItemToCartForm リクエストパラメータから送られてくる値
	 * @param userId ログイン中のユーザーid
	 */
	public void addItem(AddItemToCartForm addItemToCartForm, Integer userId, @AuthenticationPrincipal LoginUser loginUser) {
		
		//ordersテーブルからログイン中のユーザーid(ログインしていない場合はセッションidをハッシュ化)を元にstatusが0(注文前)のデータを検索する
		//データがなければ必要条件をセットしデータを挿入する、再度ログイン中のユーザーidを元にstatusが0(注文前)のデータを検索する
		//orderListに入った1件のオブジェクトを取得する
		List<Order> orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
		if(orderList.size() == 0) {
			Order order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			orderRepository.insert(order);
			orderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
		}
		Order order = orderList.get(0);		
		
		//注文された商品(orderItem)をorder_itemsテーブルに挿入しorderにセットする
		//orderItemの情報を入れる(商品id, サイズ, 数量はaddItemToCartFormから取得可能) 注文id, 商品, 注文したトッピングリストを取得しorderにセットする
		OrderItem orderItem = new OrderItem();
		BeanUtils.copyProperties(addItemToCartForm, orderItem);
		//注文idをセット
		orderItem.setOrderId(order.getId());
		//order_itemsテーブルに挿入する為のフィールド変数へのセットが出来た為データ挿入
		//挿入したデータの戻り値を受け取る(別途使用)
		OrderItem OrderItemForGetId = orderItemRepository.save(orderItem);
		//商品をセット
		orderItem.setItem(itemRepository.findById(orderItem.getItemId()));
		
		//orderToppingのリストを作成する
		//商品詳細画面でクリックされたチェックボックスのトッピングidをformToppingListに格納する
		List<Integer> formToppingList = addItemToCartForm.getToppingList();
		if(formToppingList == null) {
			formToppingList = new ArrayList<>();
		}		
		//orderToppingを複数格納する為のorderToppingListを作成
		//配列を回し、order_toppingsテーブルにデータ挿入、orderToppingListをセットする
		//orderItemにorderToppingListをセットする
		List<OrderTopping> orderToppingList = new ArrayList<>();
		for(int i = 0; i < formToppingList.size(); i++) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setToppingId(formToppingList.get(i));
			orderTopping.setOrderItemId(OrderItemForGetId.getId());
			orderTopping.setTopping(toppingRepository.findById(formToppingList.get(i)));
			orderToppingRepository.insert(orderTopping);
			orderToppingList.add(orderTopping);
		}
		orderItem.setList(orderToppingList);
		
		//注文商品の合計金額を計算する
		Integer itemPrice;			
		if(orderItem.getSize() == 'M') {
			itemPrice = orderItem.getItem().getPriceM() + orderItem.getSubTotal();
		}else {
			itemPrice = orderItem.getItem().getPriceL() + orderItem.getSubTotal();
		}		
		itemPrice = itemPrice * orderItem.getQuantity();
		Integer totalPrice = order.getTotalPrice() + itemPrice;
		order.setTotalPrice(totalPrice);
		
		//ordersテーブルのデータを更新する
		orderRepository.update(order);
		
		//ログインしていない場合は更新したorder情報をsessionスコープに格納する
		if(loginUser == null) {
			List<Order> sessionOrderList = orderRepository.findByUserIdAndStatusForOrder(userId, 0);
			Order sessionOrder = sessionOrderList.get(0);
			session.setAttribute("sessionOrder", sessionOrder);
		}
	}
}
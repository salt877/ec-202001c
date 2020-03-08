package jp.co.example.ecommerce_c.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.OrderTopping;
import jp.co.example.ecommerce_c.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		int beforeOrderId = 0;
		int beforeOrderItemId = 0;
		while (rs.next()) {
			int nowOrderId = rs.getInt("o_id");
			if (nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("o_user_id"));
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setOrderDate(rs.getDate("o_order_date"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o_destination_address"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o_delivery_time"));
				order.setPaymentMethod(rs.getInt("o_payment_method"));
				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}
			int nowOrderItemId = rs.getInt("oi_id");
			if (nowOrderItemId != beforeOrderItemId && rs.getInt("oi_id") != 0) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				String size = rs.getString("oi_size");
				char[] charSize = size.toCharArray();
				orderItem.setSize(charSize[0]);
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);
				orderToppingList = new ArrayList<OrderTopping>();
				orderItem.setList(orderToppingList);
				orderItemList.add(orderItem);
			}
			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			beforeOrderId = nowOrderId;
			beforeOrderItemId = nowOrderItemId;
		}
		return orderList;
	};

	/**
	 * 注文履歴を表示するためのリザルトセットエクストラクター
	 */
	private static final ResultSetExtractor<List<Order>> ORDERHISTORY_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		int beforeUserId = 0;
		int beforeOrderItemId = 0;
		while (rs.next()) {
			int nowUserId = rs.getInt("o_user_id");
			if (nowUserId != beforeUserId) {
				Order order = new Order();
				order.setId(rs.getInt("o_id"));
				order.setUserId(nowUserId);
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setOrderDate(rs.getDate("o_order_date"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o_destination_address"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o_delivery_time"));
				order.setPaymentMethod(rs.getInt("o_payment_method"));
				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}
			int nowOrderItemId = rs.getInt("oi_id");
			if (nowOrderItemId != beforeOrderItemId && rs.getInt("oi_id") != 0) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				String size = rs.getString("oi_size");
				char[] charSize = size.toCharArray();
				orderItem.setSize(charSize[0]);
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);
				orderToppingList = new ArrayList<OrderTopping>();
				orderItem.setList(orderToppingList);
				orderItemList.add(orderItem);
			}
			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			beforeUserId = nowUserId;
			beforeOrderItemId = nowOrderItemId;
		}
		return orderList;
	};
	
//	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
//		Order order = new Order();
//		order.setId(rs.getInt("id"));
//		order.setUserId(rs.getInt("user_id"));
//		order.setStatus(rs.getInt("status"));
//		order.setTotalPrice(rs.getInt("total_price"));
//		order.setOrderDate(rs.getDate("order_date"));
//		order.setDestinationName(rs.getString("destination_name"));
//		order.setDestinationEmail(rs.getString("destination_email"));
//		order.setDestinationZipcode(rs.getString("destination_zipcode"));
//		order.setDestinationAddress(rs.getString("destination_address"));
//		order.setDestinationTel(rs.getString("destination_tel"));
//		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
//		order.setPaymentMethod(rs.getInt("payment_method"));
//		return order;
//	};

//	/**
//	 * 引数のuserIdとstatusに一致した注文情報を取得する.
//	 * 
//	 * @param userId ユーザーid
//	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
//	 * @return 引数のuserIdとstatusに一致した注文情報
//	 */
//	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
//		String sql = "SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method FROM orders WHERE user_id = :userId AND status = :status;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
//		return template.query(sql, param, ORDER_ROW_MAPPER);
//	}

	/**
	 * ordersテーブルに注文情報を挿入する.
	 * 
	 * @param order 注文情報
	 */
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String insertSql = "INSERT INTO orders(user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method) VALUES(:userId, 0, :totalPrice, :orderDate, :destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, :destinationTel, :deliveryTime, :paymentMethod)";
		template.update(insertSql, param);
	}

	/**
	 * 引数のuserIdとstatusに一致した注文情報を取得する.
	 * 
	 * @param userId ユーザーid
	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
	 * @return 引数のuserIdとstatusに一致した注文情報
	 */
	public List<Order> findByUserIdAndStatusForOrder(Integer userId, Integer status) {
		String insertSql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id, oi.quantity oi_quantity, oi.size oi_size, ot.id ot_id, ot.topping_id ot_topping_id, ot.order_item_id ot_order_item_id, t.id t_id, t.name t_name, t.price_m t_price_M, t.price_l t_price_L FROM orders o LEFT JOIN order_items oi ON o.id = oi.order_id LEFT JOIN items i ON oi.item_id = i.id LEFT JOIN order_toppings ot ON oi.id = ot.order_item_id LEFT JOIN toppings t ON topping_id = t.id WHERE o.user_id = :userId AND o.status = :status ORDER BY oi.id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(insertSql, param, ORDER_RESULT_SET_EXTRACTOR);
	}

	/**
	 * 引数のuserIdに一致した注文履歴情報を取得する.
	 * 
	 * @param userId ユーザーid
	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
	 * @return 引数のuserIdに一致した注文履歴情報
	 */
	public List<Order> findByUserIdAndStatusForOrderHistory(Integer userId, Integer status) {
		String insertSql = "SELECT o.id o_id, o.user_id o_user_id, o.status o_status, o.total_price o_total_price, o.order_date o_order_date, o.destination_name o_destination_name, o.destination_email o_destination_email, o.destination_zipcode o_destination_zipcode, o.destination_address o_destination_address, o.destination_tel o_destination_tel, o.delivery_time o_delivery_time, o.payment_method o_payment_method, i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id, oi.quantity oi_quantity, oi.size oi_size, ot.id ot_id, ot.topping_id ot_topping_id, ot.order_item_id ot_order_item_id, t.id t_id, t.name t_name, t.price_m t_price_M, t.price_l t_price_L FROM orders o LEFT JOIN order_items oi ON o.id = oi.order_id LEFT JOIN items i ON oi.item_id = i.id LEFT JOIN order_toppings ot ON oi.id = ot.order_item_id LEFT JOIN toppings t ON topping_id = t.id WHERE o.user_id = :userId AND o.status != :status ORDER BY oi.id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(insertSql, param, ORDERHISTORY_RESULT_SET_EXTRACTOR);
	}

	/**
	 * 合計金額を変更する.
	 * 
	 * @param order orderインスタンス
	 */
	public void update(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String updateSql = "UPDATE orders SET total_price = :totalPrice WHERE user_id = :userId AND status = :status;";
		template.update(updateSql, param);
	}

	/**
	 * 注文確定時に、テーブルのデータを更新する.
	 * 
	 * @param order orderインスタンス
	 */
	public void updateOrder(Order order) {
		System.out.println(order);
		if (order.getPaymentMethod() == 1) {
			SqlParameterSource param = new BeanPropertySqlParameterSource(order);
			String insertSql = "UPDATE orders SET status = 1, total_price = :totalPrice, order_date = :orderDate, destination_name = :destinationName, destination_email = :destinationEmail, destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, destination_tel = :destinationTel, delivery_time = :deliveryTime, payment_method = :paymentMethod WHERE user_id = :userId AND status = 0;";
			template.update(insertSql, param);
		} else {
			SqlParameterSource param = new BeanPropertySqlParameterSource(order);
			String insertSql = "UPDATE orders SET status = 2, total_price = :totalPrice, order_date = :orderDate, destination_name = :destinationName, destination_email = :destinationEmail, destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, destination_tel = :destinationTel, delivery_time = :deliveryTime, payment_method = :paymentMethod WHERE user_id = :userId AND status = 0;";
			template.update(insertSql, param);
		}
	}

	/**
	 * 合計金額から引数の金額を引きデータを更新する
	 * 
	 * @param userId userId
	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
	 * @param price  金額
	 */
	public void subtractTotalPrice(Integer userId, Integer price) {
		String updateSql = "UPDATE orders SET total_price = total_price - :price WHERE user_id = :userId AND status = 0";
		SqlParameterSource param = new MapSqlParameterSource().addValue("price", price).addValue("userId", userId);
		template.update(updateSql, param);
	}
	
	/**
	 * ユーザーidに一致したデータを削除するメソッド.
	 * 
	 * @param userId ユーザーid
	 */
	public void deleteByUserId(Integer userId) {
		String deleteSql = "DELETE FROM orders WHERE user_id = :userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		template.update(deleteSql, param);
	}
	
	/**
	 * 合計金額を変更するメソッド.
	 * 
	 * @param totalPrice 合計金額
	 * @param id id
	 */
	public void updateTotalPrice(Integer totalPrice, Integer id) {
		String updateSql = "UPDATE orders SET total_price = total_price + :totalPrice WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("totalPrice", totalPrice).addValue("id", id);
		template.update(updateSql, param);
	}
	
//	public Order findByOrderId(Integer id) {
//		String loadsql = "SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, "
//				+ "destination_zipcode, destination_address, destination_tel, delivery_time, payment_method "
//				+ "FROM orders WHERE id = :id;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id); 
//		return template.queryForObject(loadsql, param, ORDER_ROW_MAPPER);
//	}
}
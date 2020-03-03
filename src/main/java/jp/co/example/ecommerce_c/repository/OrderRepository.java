package jp.co.example.ecommerce_c.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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
		Order order = new Order();
		long beforeOrderId = 0;
		while (rs.next()) {
			int nowOrderId = rs.getInt("o_id");
			if (nowOrderId != beforeOrderId) {
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				orderItemList = new ArrayList<OrderItem>();
				orderList.add(order);
			}
			if (rs.getInt("oi_id") != 0) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				String size = rs.getString("size");
				char[] charSize = size.toCharArray();
				orderItem.setSize(charSize[0]);
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("image_path"));
				item.setDeleted(rs.getBoolean("deleted"));
				orderItem.setItem(item);
				orderToppingList = new ArrayList<OrderTopping>();
				if(rs.getInt("ot_id") != 0) {
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setId(rs.getInt("ot_id"));
					orderTopping.setToppingId(rs.getInt("topping_id"));
					orderTopping.setOrderItemId(rs.getInt("order_item_id"));
					Topping topping = new Topping();
					topping.setId(rs.getInt("t_id"));
					topping.setName(rs.getString("t_name"));
					topping.setPriceM(rs.getInt("t_price_m"));
					topping.setPriceL(rs.getInt("t_price_l"));
					orderTopping.setTopping(topping);
					orderToppingList.add(orderTopping);
				}
				orderItem.setList(orderToppingList);
				orderItemList.add(orderItem);
				order.setOrderItemList(orderItemList);
			}
			beforeOrderId = nowOrderId;
		}
		return orderList;
	};
	
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) ->{
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};
	
	/**
	 * 引数のuserIdとstatusに一致した注文情報を取得する.
	 * 
	 * @param userId ユーザーid
	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
	 * @return 引数のuserIdとstatusに一致した注文情報
	 */
	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method FROM orders WHERE user_id = :userId AND status = :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(sql, param, ORDER_ROW_MAPPER);
	}
		
	/**
	 * ordersテーブルに注文情報を挿入する.
	 * 
	 * @param order 注文情報
	 */
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String insertSql = "INSERT INTO orders(user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, "
				+ "destination_address, destination_tel, delivery_time, payment_method) "
				+ "VALUES(:userId, :status, :totalPrice, :orderDate, :destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, "
				+ ":destinationTel, :deliveryTime, :paymentMethod);";
		template.update(insertSql, param);
	}
	
	public List<Order> findByUserIdAndStatusForOrder(Integer userId, Integer status){
		String insertSql = "SELECT o.id o_id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method, i.id i_id, i.name i_name, description, i.price_m i_price_m, i.price_l i_price_l, image_path, deleted, oi.id oi_id, item_id, order_id, quantity, size, ot.id ot_id, topping_id, order_item_id, t.id t_id, t.name t_name, t.price_m t_price_M, t.price_l t_price_L FROM orders o FULL OUTER JOIN order_items oi ON o.id = order_id FULL OUTER JOIN items i ON item_id = i.id FULL OUTER JOIN order_toppings ot ON oi.id = order_item_id FULL OUTER JOIN toppings t ON topping_id = t.id WHERE user_id = :userId AND status = :status ORDER BY order_item_id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(insertSql, param, ORDER_RESULT_SET_EXTRACTOR);
	}
	
	public void update(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String updateSql = "UPDATE orders SET total_price = :totalPrice WHERE user_id = :userId AND status = :status;";
		template.update(updateSql, param);
	}
}

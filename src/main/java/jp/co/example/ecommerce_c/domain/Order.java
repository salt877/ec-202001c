package jp.co.example.ecommerce_c.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 注文情報を表すドメイン.
 * 
 * @author hatakeyamakouta
 *
 */
public class Order {

	/** id(主キー) */
	private Integer id;
	
	/** ユーザーid */
	private Integer userId;
	
	/** 状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル) */
	private Integer status;
	
	/** 合計金額(税抜) */
	private Integer totalPrice;
	
	/** 注文日 */
	private Date orderDate;
	
	/** 宛先氏名 */
	private String destinationName;
	
	/** 宛先Eメール */
	private String destinationEmail;
	
	/** 宛先郵便番号 */
	private String destinationZipcode;
	
	/** 宛先住所 */
	private String destinationAddress;
	
	/** 宛先電話TEL */
	private String destinationTel;
	
	/** 配送時間 */
	private Timestamp deliveryTime;
	
	/** 支払方法 */
	private Integer paymentMethod;
	
	/** ユーザー */
	private User user;
	
	/** 注文リスト */
	private List<OrderItem> orderItemList;
	
	/**
	 * 消費税を計算する.
	 * 
	 * @return 消費税
	 */
	public int getTax() {
		return (int) (totalPrice * 0.1);
	}
	
	/**
	 * 消費税と税抜き価格の合計を計算する
	 * 
	 * @return 消費税+税抜き価格
	 */
	public int getCalcTotalPrice() {
		return totalPrice + getTax();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime
				+ ", paymentMethod=" + paymentMethod + ", user=" + user + ", orderItemList=" + orderItemList + "]";
	}
}

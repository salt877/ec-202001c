package jp.co.example.ecommerce_c.form;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 注文確定時に使用するフォーム.
 * 
 * @author hatakeyamakouta
 *
 */
public class OrderForm {
	
	/** ユーザーid */
	private Integer userId;
	
	/** 状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル) */
	private Integer status;
	
	/** 合計金額(税抜) */
	private Integer totalPrice;
	
	/** 宛先氏名 */
	@NotBlank(message="お名前を入力して下さい")
	private String destinationName;
	
	/** 注文日 */
	private Date orderDate;
	
	/** 宛先Eメール */
	@NotBlank(message="メールアドレスを入力して下さい")
	@Email(message="メールアドレスの形式ではありません")
	private String destinationEmail;
	
	/** 宛先郵便番号 */
	@Size(min=7, max=7, message="7桁で入力して下さい")
	@Pattern(regexp="[0-9]+", message="郵便番号が不正です")
	private String destinationZipcode;
	
	/** 宛先住所 */
	@NotBlank(message="住所を入力して下さい")
	private String destinationAddress;
	
	/** 宛先電話TEL */
	@NotBlank(message="電話番号を入力して下さい")
	@Size(min=10, max=11, message="10桁以上11桁以下で入力して下さい")
	@Pattern(regexp="[0-9]+", message="電話番号が不正です")
	private String destinationTel;	

	/** 配送希望日 */
	@NotNull(message="この日時に配達することはできません")
	private Date deliveryDate;

	/** 配送時間 */
	private Integer deliveryTime;
	
	/** 支払方法 */
	private Integer paymentMethod;
	
	/** カード番号(数字14桁-16桁) */
	private String card_number;
	
	/** カード有効期限(月)(数字2桁) */
	private String card_exp_month;
	
	/** カード有効期限(年)(数字4桁) */
	private String card_exp_year;
	
	/** セキュリティコード(数字3桁-4桁) */
	private String card_cvv;
	
	/** カード名義人名 */
	private String card_name;

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

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getCard_exp_month() {
		return card_exp_month;
	}

	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public String getCard_exp_year() {
		return card_exp_year;
	}

	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public String getCard_cvv() {
		return card_cvv;
	}

	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	@Override
	public String toString() {
		return "OrderForm [userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", destinationName=" + destinationName + ", orderDate=" + orderDate + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate
				+ ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod + ", card_number=" + card_number
				+ ", card_exp_month=" + card_exp_month + ", card_exp_year=" + card_exp_year + ", card_cvv=" + card_cvv
				+ ", card_name=" + card_name + "]";
	}	
}

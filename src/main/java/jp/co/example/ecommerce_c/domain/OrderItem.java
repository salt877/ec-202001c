package jp.co.example.ecommerce_c.domain;

import java.util.List;

/**
 * 注文商品を表すドメイン.
 * 
 * @author hatakeyamakouta
 *
 */
public class OrderItem {

	/** id(主キー) */
	private Integer id;
	
	/** 商品id */
	private Integer itemId;
	
	/** 注文id */
	private Integer orderId;
	
	/** 数量 */
	private Integer quantity;
	
	/** サイズ */
	private Character size;
	
	/** 商品 */
	private Item item;
	
	/** 注文したトッピング */
	private List<OrderTopping> list;
	
	/**
	 * トッピングの価格を計算する.
	 * 
	 * @return トッピングの価格
	 */
	public int getSubTotal() {
		if(size == 'M') {
			return list.size() * 200;
		}else {
			return list.size() * 300;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getList() {
		return list;
	}

	public void setList(List<OrderTopping> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", list=" + list + "]";
	}
}

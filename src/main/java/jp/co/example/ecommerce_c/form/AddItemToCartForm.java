package jp.co.example.ecommerce_c.form;

import java.util.List;

/**
 * 商品詳細からカートに商品を入れる際に使用するフォーム.
 * 
 * @author hatakeyamakouta
 *
 */
public class AddItemToCartForm {

	/** 商品id */
	private Integer itemId;
	
	/** サイズ */
	private Character size;
	
	/** トッピングリスト */
	private List<Integer> toppingList;
	
	/** 数量 */
	private Integer quantity;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Character getSize() {
		return size;
	}
	public void setSize(Character size) {
		this.size = size;
	}
	public List<Integer> getToppingList() {
		return toppingList;
	}
	public void setToppingList(List<Integer> toppingList) {
		this.toppingList = toppingList;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "CartForm [itemId=" + itemId + ", size=" + size + ", toppingList=" + toppingList + ", quantity="
				+ quantity + "]";
	}
}

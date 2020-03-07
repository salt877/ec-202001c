package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.repository.ItemRepository;

/**
 * 商品一覧画面を操作するサービス.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class ShowItemListService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報を全件検索します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> showList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

	/**
	 * 商品名から商品を(曖昧)検索します.
	 * 
	 * @param name 商品名
	 * @return 検索された商品の情報一覧
	 */
	public List<Item> searchByItemName(String name) {
		return itemRepository.findByItemName(name);
	}

	/**
	 * ページング機能.
	 * 
	 * @param page 表示させたいページ数
	 * @param size 1ページに表示させる商品数
	 * @return
	 * @return 1ページに表示されるサイズ分の商品一覧情報
	 */
	public Page<List<Item>> showItemListPaging(int page, int size, List<List<Item>> itemList) {
		// 表示させたいページ数を-1しないと動作しない
		page--;
		// どの商品から表示させるかというカウント値
		int startItemCount = page * size;
		// 絞り込んだ後の商品リストが入る変数
		List<List<Item>> list;

		if (itemList.size() < startItemCount) {
			// (ありえないが)もし表示させたい商品カウントがサイズよりも大きい場合は空のリストを返す
			list = Collections.emptyList();
		} else {
			// 該当ページに表示させる商品一覧を作成
			int toIndex = Math.min(startItemCount + size, itemList.size());
			list = itemList.subList(startItemCount, toIndex);
		}
		// 上記で作成した該当ページに表示させる従業員一覧をページングできる形に変換して返す
		Page<List<Item>> itemPage = new PageImpl<List<Item>>(list, PageRequest.of(page, size), itemList.size());
		return itemPage;
	}

	public List<Item> paging(Integer page) {
		return itemRepository.showPage(page);
	}

	/**
	 * オートコンプリート機能.
	 * 
	 * @param itemList 商品一覧
	 * @return オートコンプリート用JavaScriptの配列の文字列
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}

	/**
	 * Mサイズの商品の価格が安い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByLowerMsizePrice() {
		return itemRepository.orderByLowerMsizePrice();
	}

	/**
	 * Mサイズの商品の価格が高い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByHigherMsizePrice() {
		return itemRepository.orderByHigherMsizePrice();
	}

	/**
	 * Lサイズの商品の価格が安い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByLowerLsizePrice() {
		return itemRepository.orderByLowerLsizePrice();
	}

	/**
	 * Lサイズの商品の価格が高い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByHigherLsizePrice() {
		return itemRepository.orderByHigherLsizePrice();
	}

}

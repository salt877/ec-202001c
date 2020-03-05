package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.service.ShowItemListService;

/**
 * 商品一覧情報を出力するコントローラ.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	// 1ページに表示する商品数は9個
	private static final int VIEW_SIZE = 9;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param model      モデル
	 * @param page       出力したいページ数
	 * @param searchName 検索文字列
	 * @param model      モデル
	 * @param page       出力したいページ数
	 * @param searchName 検索文字列
	 * @return 商品一覧画面
	 */
	@RequestMapping("/")
	public String showItemList(Model model, Integer page, String searchName) {

		//itemList =　商品数
		List<Item> itemList = showItemListService.showList();

		// 一覧を揃える
		List<List<Item>> AllItemList = new ArrayList<>();
		List<Item> threeItem = new ArrayList<>();

		for (int i = 1; i <= itemList.size(); i++) {
			threeItem.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				AllItemList.add(threeItem);
				threeItem = new ArrayList<>();
			}
			model.addAttribute("AllItemList", AllItemList);	
		}
		// 商品名検索機能
		if (searchName != null) {
			itemList = showItemListService.searchByItemName(searchName);
			// ページングの数字からも検索できるように検索文字列をスコープに格納しておく
			model.addAttribute("searchName", searchName);
			// 検索結果を横並びにする
			List<List<Item>> searchItemList = new ArrayList<>();
			for (int i = 1; i <= itemList.size(); i++) {
				threeItem.add(itemList.get(i - 1));
				if (i % 3 == 0 || i == itemList.size()) {
					AllItemList.add(threeItem);
					threeItem = new ArrayList<>();
				}
			}
			model.addAttribute("AllItemList", searchItemList);
		}
		if (itemList.size() == 0) {
			model.addAttribute("searchName", searchName);
			model.addAttribute("message", "該当する商品がありません");
			itemList = showItemListService.showList();
		}
		
		// オートコンプリート
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		return "item_list";
	}
	
	@RequestMapping("/paging")
	public String paging(Integer page,Model model) {
		
		List<Item> itemList = showItemListService.showList();
		//OFFSETの値が0,9,18,27など9で割り切れる時にページ遷移	
		if (itemList.size() % 9 == 0) {
			List<Item> itemList2 = showItemListService.paging(page);
			
			List<List<Item>> AllItemList = new ArrayList<>();
			List<Item> threeItem = new ArrayList<>();
			for (int i = 1; i <= itemList2.size(); i++) {
				threeItem.add(itemList2.get(i - 1));
				if (i % 3 == 0 || i == itemList2.size()) {
					AllItemList.add(threeItem);
					threeItem = new ArrayList<>();
				}
				model.addAttribute("AllItemList", AllItemList);
			}
	}
		return "item_list";
	}
	

	/**
	 * Mサイズの商品を価格が安い順番で表示します.
	 * 
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/lower-price-m")
	public String LowerMsize(Model model) {
		List<Item> itemList = showItemListService.orderByLowerMsizePrice();

		// 横に3件ずつ表示する
		List<List<Item>> AllItemList = new ArrayList<>();
		List<Item> threeItem = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			threeItem.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				AllItemList.add(threeItem);
				threeItem = new ArrayList<>();
			}
		}
		model.addAttribute("AllItemList", AllItemList);
		return "item_list";
	}

	/**
	 * Mサイズの商品を価格が高い順番で表示します.
	 * 
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/higher-price-m")
	public String HigherMsize(Model model) {
		List<Item> itemList = showItemListService.orderByHigherMsizePrice();

		// 横に3件ずつ表示する
		List<List<Item>> AllItemList = new ArrayList<>();
		List<Item> threeItem = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			threeItem.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				AllItemList.add(threeItem);
				threeItem = new ArrayList<>();
			}
		}
		model.addAttribute("AllItemList", AllItemList);
		return "item_list";
	}

	/**
	 * Lサイズの商品を価格が安い順番で表示します.
	 * 
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/lower-price-l")
	public String LowerLsize(Model model) {
		List<Item> itemList = showItemListService.orderByLowerLsizePrice();

		// 横に3件ずつ表示する
		List<List<Item>> AllItemList = new ArrayList<>();
		List<Item> threeItem = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			threeItem.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				AllItemList.add(threeItem);
				threeItem = new ArrayList<>();
			}
		}
		model.addAttribute("AllItemList", AllItemList);
		return "item_list";
	}

	/**
	 * Lサイズの商品を価格が高い順番で表示します.
	 * 
	 * @param model リクエストスコープ
	 * @return 商品一覧画面
	 */
	@RequestMapping("/higher-price-l")
	public String HigherLsize(Model model) {
		List<Item> itemList = showItemListService.orderByHigherLsizePrice();

		// 横に3件ずつ表示する
		List<List<Item>> AllItemList = new ArrayList<>();
		List<Item> threeItem = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			threeItem.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				AllItemList.add(threeItem);
				threeItem = new ArrayList<>();
			}
		}
		model.addAttribute("AllItemList", AllItemList);
		return "item_list";
	}
}

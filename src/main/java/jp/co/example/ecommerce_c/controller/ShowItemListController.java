package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.form.SortItemListForm;
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

	@ModelAttribute
	public SortItemListForm setUpSortItemListForm() {
		return new SortItemListForm();
	}

//	// 1ページに表示する商品数は9個
//	private static final int VIEW_SIZE = 9;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param model      モデル
	 * @param page       出力したいページ数
	 * @param searchName 検索文字列
	 * @return 商品一覧画面
	 */
	@RequestMapping("/")
	public String showItemList(Model model, Integer page, String searchName) {

		List<Item> itemList = showItemListService.showList();

		// List<Item> itemList = showItemListService.paging(page);
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
					searchItemList.add(threeItem);
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

//		// 表示させたいページ数、ページサイズ、商品リストを渡し１ページに表示させる商品リストを絞り込み
//		Page<Item> itemPage = showItemListService.showItemListPaging(page, VIEW_SIZE, itemList);
//		model.addAttribute("itemPage", itemPage);
//
//		// ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
//		List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
//		model.addAttribute("pageNumbers", pageNumbers);

		// オートコンプリート
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		return "item_list";
	}

	/**
	 * ページングのリンクに使うページ数をスコープに格納 (例)28件あり1ページにつき10件表示させる場合→1,2,3がpageNumbersに入る
	 * 
	 * @param model        モデル
	 * @param employeePage ページング情報
	 */
//	private List<Integer> calcPageNumbers(Model model, Page<Item> itemPage) {
//		int totalPages = itemPage.getTotalPages();
//		List<Integer> pageNumbers = null;
//		if (totalPages > 0) {
//			pageNumbers = new ArrayList<Integer>();
//			for (int i = 1; i <= totalPages; i++) {
//				pageNumbers.add(i);
//			}
//		}
//		return pageNumbers;
//	}

	@RequestMapping("/paging")
	public String paging(Integer page, Model model) {
		System.out.println(page);
		List<Item> itemList = showItemListService.paging(page);
//		//OFFSETの値が0,9,18,27など9で割り切れる時にページ遷移	
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

		return "item_list";
	}

	/**
	 * 選択された並び順で商品一覧を出力します.
	 * 
	 * @param form 並び順選択フォーム
	 * @param model モデル
	 * @return　商品一覧画面
	 */
	@RequestMapping("/sort")
	public String SortItemList(SortItemListForm form, Model model) {

		List<Item> itemList = null;

		if (form.getSort().equals("1")) {
			itemList = showItemListService.orderByLowerMsizePrice();
		} else if (form.getSort().equals("2")) {
			itemList = showItemListService.orderByHigherMsizePrice();
		} else if (form.getSort().equals("3")) {
			itemList = showItemListService.orderByLowerLsizePrice();
		} else if (form.getSort().equals("4")) {
			itemList = showItemListService.orderByHigherLsizePrice();
		}
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
		// オートコンプリート
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		return "item_list";
	}

}

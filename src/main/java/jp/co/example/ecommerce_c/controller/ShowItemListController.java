package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	 * @return 商品一覧画面
	 */
	@RequestMapping("/")
	public String showItemList(Model model, Integer page, String searchName) {

		// ページング機能を追加
		if (page == null) {
			page = 1;
		}
		List<Item> itemList = null;

		// 商品名検索機能
		if (searchName == null) {
			itemList = showItemListService.showList();
		} else {
			itemList = showItemListService.searchByItemName(searchName);
			model.addAttribute("searchName", searchName);
		}

		List<List<Item>> itemList1 = new ArrayList<>();
		List<Item> itemList2 = new ArrayList<>();

		for (int i = 0; i < itemList.size(); i++) {
			itemList2.add(itemList.get(i));
			if (i % 3 == 2) {
				itemList1.add(itemList2);
				itemList2 = new ArrayList<>();
			}
		}
		
		model.addAttribute("itemList1", itemList1);

		Page<Item> itemPage = showItemListService.showListPaging(page, VIEW_SIZE, itemList);
		model.addAttribute("itemPage", itemPage);

		List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
		model.addAttribute("pageNumbers", pageNumbers);

		return "item_list";
	}

	/**
	 * ページングのリンクに使うページ数をスコープに格納
	 * 
	 * @param model    モデル
	 * @param itemPage ぺージング情報
	 * @return
	 */
	public List<Integer> calcPageNumbers(Model model, Page<Item> itemPage) {
		int totalPages = itemPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

}

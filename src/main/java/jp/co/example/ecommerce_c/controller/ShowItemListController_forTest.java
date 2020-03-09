package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
public class ShowItemListController_forTest {

	@Autowired
	private ShowItemListService showItemListService;
	
	@Autowired
	private HttpSession session;

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

		//↓ページング機能により使わなくなりました
		//List<Item> itemList = showItemListService.showList();
		
		List<Item> itemList = showItemListService.paging(page);
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
		
		// オートコンプリート
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		return "item_list";
	}
	
	@RequestMapping("/paging")
	public String paging(Integer page,Model model) {
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
	
	@RequestMapping("/someway")
	public String searchSomeway(Integer forSearchNumber, Model model) {
		List<Item> itemList = showItemListService.searchBySomeway(forSearchNumber);
		Integer pageNumber = itemList.size() / 9;
		List<Integer> pageNumberList = new ArrayList<>();
		for(int i = 2; i <= pageNumber; i++) {
			pageNumberList.add(i);
		}
		model.addAttribute("pageNumberList", pageNumberList);
		
		List<List<List<Item>>> groupItemList = new ArrayList<>();
		List<List<Item>> nineItemList = new ArrayList<>();
		List<Item> threeItemList = new ArrayList<>();
		for(int i = 1; i <= itemList.size(); i++) {
			threeItemList.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				nineItemList.add(threeItemList);
				threeItemList = new ArrayList<>();
				if(i % 9 == 0 || i == itemList.size()) {
					groupItemList.add(nineItemList);
					nineItemList = new ArrayList<>();
				}
			}
		}
		session.setAttribute("groupItemList", groupItemList);
		model.addAttribute("nineItemList", groupItemList.get(0));
		return "item_list";
	}
	
	@RequestMapping("/to_other_page")
	public String toOtherPage(Integer page, Model model) {
		List<Item> itemList = showItemListService.showList();
		Integer pageNumber = itemList.size() / 9;
		List<Integer> pageNumberList = new ArrayList<>();
		for(int i = 1; i <= pageNumber; i++) {
			if(i == page) {
				continue;
			}
			pageNumberList.add(i);
		}
		model.addAttribute("pageNumberList", pageNumberList);
		
		List<List<List<Item>>> groupItemList = (List<List<List<Item>>>) session.getAttribute("groupItemList");
		model.addAttribute("nineItemList", groupItemList.get(page - 1));
		return "item_list";
	}
}

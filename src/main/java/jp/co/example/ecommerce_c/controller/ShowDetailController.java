package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.service.ShowDetailService;

/**
 * 商品詳細を管理するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/show-detail")
public class ShowDetailController {

	@Autowired
	private ShowDetailService showDetailService;
	
	/**
	 * ???リクエストスコープよりアプリケーションスコープのが良い???
	 * 商品詳細画面を出力.
	 *
	 * @param id リクエストパラメータで送られてくる商品ID
	 * @param model モデル
	 * @return 商品詳細画面
	 */
	@RequestMapping("")
	public String showDetail(Integer id, Model model) {
		Item item = showDetailService.showDetail(id);
		List<Topping> toppingList = showDetailService.showTopping();
		item.setToppingList(toppingList);
		model.addAttribute("item", item);
		return "item_detail";
	}
}

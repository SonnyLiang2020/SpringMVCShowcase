package mvc.views;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/views/*")
public class ViewsController {

	@GetMapping("html")
	public String prepare(Model model) {
		model.addAttribute("foo...in", "barll...");
		model.addAttribute("fruit...in", "applell..");
		return "views/html";
	}

	@GetMapping("/viewName")	//??????????????
	public void usingRequestToViewNameTranslator(Model model) {
		model.addAttribute("fooin", "barll");
		model.addAttribute("fruitin", "applell");
	}

	@GetMapping("pathVariable/{foo}/{fruit}")
	public String pathVars(@PathVariable String foo, @PathVariable String fruit) {
		return "views/html";
	}

	@GetMapping("dataBinding/{foo}/{fruit}")
	public String dataBinding(@Valid JavaBean javaBean, Model model) {
		return "views/dataBinding";
	}

}

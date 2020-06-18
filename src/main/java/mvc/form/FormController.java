package mvc.form;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mvc.extensions.ajax.AjaxUtils;

@Controller
@RequestMapping("/form")
@SessionAttributes("formBean")
public class FormController {
	
	/**第二步执行 <其他 case 中可以没有这一步>
	 * 判断是否是 Ajax 请求
	 */
	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		System.out.println("ajaxAttribute(WebRequest request, Model model).....");
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
		FormBean t = (FormBean) model.asMap().get("formBean");
		if(t != null)
			System.out.println("in ajaxAttr ---> " + t.toString());
	}
	
	/**第一步执行
	 *	 往 model 中放入一个初始化的表单对应的 Bean
	 */
	@ModelAttribute("formBean")
	public FormBean createFormBean(Model model) {
		System.out.println(model.asMap().get("ajaxRequest"));
		System.out.println("createFormBean().....");
		FormBean fb = new FormBean();
		fb.setName("HXB");
		return fb;
	}
	
	/**第三步执行
	 *	前端表单的属性与 model 中的属性关联
	 * 
	 */
	@GetMapping
	public void form(Model model) {
		FormBean t = (FormBean) model.asMap().get("formBean");
		t.setInquiry(InquiryType.suggestion);
		model.addAttribute(t);
		System.out.println("form().....");
		System.out.println("in the model : ---> " + t.toString());
	}
	
	/**第四步执行
	 * 处理前端页面提交的 Post 请求
	 */
	@PostMapping
	public String processSubmit(@Valid FormBean formBean, BindingResult result,
			@ModelAttribute("ajaxRequest") boolean ajaxRequest,
			Model model, RedirectAttributes redirectAttrs) {
//		FormBean t = (FormBean) model.asMap().get("formBean");
//		t.setAge(1111);
		System.out.println("processSubmit().....");
		System.out.println(formBean.toString());
		if(result.hasErrors())
			return null;
		String message = "Form submitted successfully, Bound " + formBean;
		if(ajaxRequest) {
			model.addAttribute("message", message);
			return null;
		}else {
			redirectAttrs.addFlashAttribute("message", message);
			return "redirect:/form";
		}
	}

}

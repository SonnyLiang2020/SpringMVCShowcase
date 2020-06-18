package mvc.validation;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {
	
	@GetMapping("/validate")
	public String validate(@Valid JavaBean bean, BindingResult result) {
		if(result.hasErrors())
			return "Object has validation errors...";
		else
			return "No errors";
	}

}

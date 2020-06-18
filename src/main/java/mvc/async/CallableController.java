package mvc.async;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;

@Controller
@RequestMapping("/async/callable")
public class CallableController {

	@GetMapping("/response-body")
	public @ResponseBody Callable<String> callable() {
		return new Callable<String>() {

			public String call() throws Exception {
				Thread.sleep(2000);
				return "Callable result";
			}
		};
	}

	@GetMapping("/view")
	public Callable<String> callableWithView(final Model model) {
		return () -> {
			Thread.sleep(2000);
			model.addAttribute("foo", "bar in callableWithView...");
			model.addAttribute("fruit", "apple in callableWithView...");
			return "views/html";
		};
	}

	@GetMapping("/exception")
	public @ResponseBody Callable<String> callableWithException(
			final @RequestParam(required = false, defaultValue = "true") boolean handled) {
		return () -> {
			Thread.sleep(2000);
			if (handled) {
				throw new IllegalStateException("Callable errors...with StateException");
			} else {
				throw new IllegalArgumentException("Callable errors...with ArgumentException");
			}
		};
	}

	@GetMapping("/custom-timeout-handling")
	public @ResponseBody WebAsyncTask<String> callableWithCustomTimeoutHandling() {
		Callable<String> callable = () -> {
			Thread.sleep(2000);
			return "Callable result...in custom-timeout-handling...";
		};
		return new WebAsyncTask<String>(1000, callable);
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled Staexception..." + ex.getMessage();
	}

	@ExceptionHandler
	@ResponseBody
	public String selfAdd(IllegalArgumentException ex) {
		return "Handled Argexception..." + ex.getMessage();
	}

}

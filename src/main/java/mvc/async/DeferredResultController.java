package mvc.async;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/async")
public class DeferredResultController {

	private final Queue<DeferredResult<String>> responseBodyQueue = new ConcurrentLinkedDeque<>();

	private final Queue<DeferredResult<ModelAndView>> mavQueue = new ConcurrentLinkedDeque<>();

	private final Queue<DeferredResult<String>> exceptionQueue = new ConcurrentLinkedDeque<>();

	@GetMapping("/deferred-result/response-body")
	public @ResponseBody DeferredResult<String> deferredResult() {
		DeferredResult<String> result = new DeferredResult<>();
		this.responseBodyQueue.add(result);
		return result;
	}

	@GetMapping("/deferred-result/model-and-view")
	public @ResponseBody DeferredResult<ModelAndView> deferredRusltWithView() {
		DeferredResult<ModelAndView> result = new DeferredResult<>();
		this.mavQueue.add(result);
		return result;
	}
	
	@GetMapping("/deferred-result/exception")
	public @ResponseBody DeferredResult<String> deferredResultWithException(){
		DeferredResult<String> result = new DeferredResult<>();
		this.exceptionQueue.add(result);
		return result;
	}

	@GetMapping("/deferred-result/timeout-value")
	public @ResponseBody DeferredResult<String> deferredResultWithTimeoutValue() {
		return new DeferredResult<>(1000L, "Deferred result after timeout...");
	}

	@Scheduled(fixedRate = 2000)
	public void processQueues() {
		for (DeferredResult<String> result : this.responseBodyQueue) {
			result.setResult("Deferred result in responseBodyQueue...");
			this.responseBodyQueue.remove(result);
		}
		for (DeferredResult<String> result : this.exceptionQueue) {
			result.setResult("Deferred result in exceptionQueue...");
			this.exceptionQueue.remove(result);
		}
		for (DeferredResult<ModelAndView> result : this.mavQueue) {
			result.setResult(new ModelAndView("views/html", "JavaBean", new JavaBean("bar in Mav", "apple in Mav")));
			this.mavQueue.remove(result);
		}
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled exception: " + ex.getMessage();
	}

}

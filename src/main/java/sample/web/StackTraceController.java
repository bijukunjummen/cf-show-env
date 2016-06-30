package sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StackTraceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StackTraceController.class);

	@RequestMapping("/triggerStack")
	@ResponseBody
	public String stackTrace() {
		LOGGER.error("Testing", new RuntimeException("Throwing an exception!"));
		return "test";
	}
}

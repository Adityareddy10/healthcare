package com.healthcare.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
        return "redirect:/role-selector.html";
	}

	@GetMapping("/health")
	@ResponseBody
	public String health() {
		return "Service is running";
	}

}

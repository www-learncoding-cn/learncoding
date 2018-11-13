package cn.learncoding.springmvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author learncoding.cn
 */
@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}
}

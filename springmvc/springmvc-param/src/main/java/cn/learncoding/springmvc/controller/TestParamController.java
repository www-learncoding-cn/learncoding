package cn.learncoding.springmvc.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.learncoding.springmvc.vo.User;

/**
 * @author learncoding.cn
 */
@RestController
public class TestParamController {

	@GetMapping("/hello")
	public String hello(String name, Integer age) {
		return "hello " + name + ", age: " + age;
	}
	
	@GetMapping("/hello1")
	public String hello1(User user) {
		return "hello " + user.getName() + ", age: " + user.getAge();
	}
	
	@GetMapping("/hello2")
	public String hello2(@RequestParam(value="name") String username, 
			@RequestParam(value="age") String userAge) {
		return "hello " + username + ", age: " + userAge;
	}
	
	@GetMapping("/hello3")
	public String hello3(HttpServletRequest request) {
		return "hello " + request.getParameter("name") + ", age: " + request.getParameter("age");
	}
	
	//使用@RequestBody时 最好采用Post方式，因为get方式参数可能在URL后面
	@PostMapping("/hello4")
	public String hello4(@RequestBody String body) {
		System.out.println(body);
		String[] data = body.split("&");
		String name = "";
		String age = "";
		for (String string : data) {
			if (string.startsWith("name=")) {
				name = string.substring(5);
			}
			if (string.startsWith("age=")) {
				age = string.substring(4);
			}
		}
		return "hello " + name + ", age: " + age;
	}
	
	@PostMapping("/hello5")
	public String hello5(@RequestBody User user) {
		return "hello " + user.getName() + ", age: " + user.getAge();
	}
	
	@GetMapping("/hello/{name}")
	public String hello6(@PathVariable("name") String name, Integer age) {
		return "hello " + name + ", age: " + age;
	}
}

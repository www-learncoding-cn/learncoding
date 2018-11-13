package cn.learncoding.springmvc.controller;


import java.util.concurrent.Callable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.learncoding.springmvc.vo.User;

/**
 * @author learncoding.cn
 */
@Controller
public class ReturnController {

	@GetMapping("/hello")
	@ResponseBody
	public User hello(User user) {
		return user;
	}
	
	@GetMapping("/hello1")
	public ResponseEntity<User> hello1(User user) {
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/hello2")
	public Callable<User> hello2(final User user) {
		return new Callable<User>() {
			public User call() throws Exception {
				return user;
			}
		};
	}
}

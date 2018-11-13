package cn.learncoding.springmvc.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.learncoding.springmvc.vo.User;

/**
 * @author learncoding.cn
 */
@RestController
public class TestValidParamVontroller {

	@GetMapping("/valid")
	public String valid(@Valid User user,BindingResult br) {
	  if(br.hasErrors()){            
            return br.getFieldError().toString();
        }
		return "hello " + user.getName() + ", age: " + user.getAge();
	}
}

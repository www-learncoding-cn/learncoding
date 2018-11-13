package cn.learncoding.springmvc.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author learncoding.cn
 */
public class User {
	
	@NotBlank
	private String name;
	
	@Min(value = 18)
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}

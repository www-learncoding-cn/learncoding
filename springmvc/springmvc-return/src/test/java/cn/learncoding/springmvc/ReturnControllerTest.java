package cn.learncoding.springmvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author learncoding.cn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(locations={"classpath:mvc-context.xml"})
public class ReturnControllerTest {

	@Resource
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @Before
    public void before(){
    	//初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); 
    }
	
	@Test
	public void testHello() throws Exception{
		this.mvc.perform(get("/hello")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.characterEncoding(StandardCharsets.UTF_8.name())
				.param("name", "test")
				.param("age", "18"))
        .andExpect(status().isOk())
        .andDo(print());  
	}
	
	@Test
	public void testHello1() throws Exception{
		this.mvc.perform(get("/hello1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.characterEncoding(StandardCharsets.UTF_8.name())
				.param("name", "test")
				.param("age", "18"))
        .andExpect(status().isOk())
        .andDo(print());   
	}
	
	@Test
	public void testHello2() throws Exception{
		MvcResult result = this.mvc.perform(get("/hello2")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.characterEncoding(StandardCharsets.UTF_8.name())
				.param("name", "test")
				.param("age", "18"))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();  
		Object user = result.getAsyncResult();
		System.out.println(user);
	}
}

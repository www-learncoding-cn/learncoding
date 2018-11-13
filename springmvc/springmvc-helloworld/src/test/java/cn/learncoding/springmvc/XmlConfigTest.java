package cn.learncoding.springmvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author learncoding.cn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(locations={"classpath:mvc-context.xml"})
public class XmlConfigTest {

	@Resource
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @Before
    public void before(){
    	//初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); 
    }
	
	@Test
	public void test() throws Exception{
		this.mvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andDo(print());  
	}
}

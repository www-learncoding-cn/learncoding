package cn.learncoding.springmvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
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
public class FileControllerTest {

	@Resource
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @Before
    public void before(){
    	//初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); 
    }
	
	@Test
	public void testFile() throws Exception{
		String body = "ABC";
		MvcResult result = this.mvc.perform(multipart("/file/upload")
				.file(new MockMultipartFile("file", "文件.txt", null, body.getBytes("UTF-8"))))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();  
		
		String filePath = result.getResponse().getContentAsString();
		
		this.mvc.perform(get("/file/download").param("filePath", filePath))
        .andExpect(status().isOk())
        .andExpect(content().string(body))
        .andDo(print());  
	}
}

package cn.learncoding.springmvc;


import cn.learncoding.springmvc.vo.Msg;
import cn.learncoding.springmvc.vo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author learncoding.cn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitWebConfig(locations={"classpath:mvc-context.xml"})
public class UserControllerTest {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final Random RANDOM = new Random();

	@Resource
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @Before
    public void before() throws Exception{
    	//初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        //初始化测试数据
		for (int i = 0; i < 3; i++) {
            String user = MAPPER.writeValueAsString(new User("test" + i, i));
			this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(user))
					.andExpect(status().isCreated());
		}
    }
	
	@Test
	public void test() throws Exception{
        //查询全部测试数据
		MvcResult result = this.mvc.perform(get("/users"))
        					.andExpect(status().isOk()).andReturn();

		String responseBody = result.getResponse().getContentAsString();
		assertNotNull(responseBody);

		Msg<List<User>> msg = MAPPER.readValue(responseBody, MAPPER.getTypeFactory().constructParametricType(
				Msg.class, MAPPER.getTypeFactory().constructParametricType(ArrayList.class, User.class)));
        List<User> list = msg.getData();
		assertNotEquals(list.size(), 0);

        //所有测试数据中，随机取出一条进行单体测试，其他进行批量更新测试
		User user = list.remove(RANDOM.nextInt(list.size()));

		for (User u : list) {
			u.setAge(u.getAge() + 10);
		}
        //批量更新
		this.mvc.perform(put("/users/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(MAPPER.writeValueAsString(list)))
				.andExpect(status().isNoContent());

		String name = "张三";
        user.setName(name);
        user.setAge(-1);
        //单体全量更新
        this.mvc.perform(put("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isNoContent());

        int age = 80;
        user.setName(null);
        user.setAge(age);
        //单体部分更新
        this.mvc.perform(patch("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(user)))
                .andExpect(status().isNoContent());
        //单体更新数据验证
        this.mvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.age").value(age))
                .andExpect(jsonPath("$.data.name").value(name));
        //删除
        this.mvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent());
        //删除验证
        this.mvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isNotFound());
	}
}

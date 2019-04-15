package com.xn.find_xn_user;

import com.xn.find_xn_user.dao.UserListDao;
import com.xn.find_xn_user.domain.RegisterResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FindXnUserApplicationTests {
	@Autowired
	UserListDao userListDao;
	@Test
	public void contextLoads() {
		RegisterResult user=new RegisterResult();
		user.setUsername("zhansaa");
		user.setpassword("44444");
		userListDao.saveUser(user);
	}

}

package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UsersDAOTest {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLogin() {
		int type=1;
		String username="21001";
		String password="21000";
		String result=UsersDAO.login(type, username, password);
		if(result.equals("false")) System.out.println("not ok!");
		else System.out.println("ok!!");
	}
}

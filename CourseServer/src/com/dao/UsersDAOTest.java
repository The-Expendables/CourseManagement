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
		boolean result=UsersDAO.login(0, "21001", "888888");
		if(result) System.out.println("ok!");
		else System.out.println("not ok!!");
	}

}

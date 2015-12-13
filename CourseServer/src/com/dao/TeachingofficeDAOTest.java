package com.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.tb.Tb_teachingoffice;

public class TeachingofficeDAOTest extends MyDAO {

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testInsert() {
//		Tb_teachingoffice tb_teachingoffice=new Tb_teachingoffice();
//		tb_teachingoffice.setId("10086");
//		tb_teachingoffice.setPassword("10086");
//		tb_teachingoffice.setPhone("10086");
//		TeachingofficeDAO.insert(tb_teachingoffice);
//	}
	
	@Test
	public void testUpdate() {
		Tb_teachingoffice tb_teachingoffice=new Tb_teachingoffice();
		tb_teachingoffice.setId("10086");
		tb_teachingoffice.setPassword("10086");
		tb_teachingoffice.setPhone("sqhsb!!!");
		TeachingofficeDAO.update(tb_teachingoffice);
	}

}

package com.qa.ims.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DBUtilsTest {

	private DBUtils dbu = new DBUtils();
	
	@Test
	public void initTest() {
		String[] paths = {"A", "B"};
		DBUtils spy = Mockito.spy(dbu);
		Mockito.doReturn(1).when(spy).executeSQLFile(Mockito.any());
		assertEquals(2, spy.init(paths));
	}
}

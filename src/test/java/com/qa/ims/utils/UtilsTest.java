package com.qa.ims.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

	private Utils utils = new Utils();
	
	@Test
	public void getLongTest() {
		Long l = 1L;
		Utils spy = Mockito.spy(utils);
		Mockito.doReturn("0", "a", "1").when(spy).getString();
		assertEquals(l, spy.getLong());
	}
	
	@Test
	public void getYNTest() {
		Utils spy = Mockito.spy(utils);
		Mockito.doReturn("x", "y").when(spy).getString();
		assertEquals("y", spy.getYN());
	}	
	
	@Test
	public void getIntTest() {
		Utils spy = Mockito.spy(utils);
		Mockito.doReturn("0", "a", "1").when(spy).getString();
		assertEquals(1, spy.getInt());
	}
	
	@Test
	public void getDoubleTest() {
		Double d = 1D;
		Utils spy = Mockito.spy(utils);
		Mockito.doReturn("0", "a", "1").when(spy).getString();
		assertEquals(d, spy.getDouble());
	}
}

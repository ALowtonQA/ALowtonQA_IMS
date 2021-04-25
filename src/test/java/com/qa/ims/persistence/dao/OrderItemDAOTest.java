package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.UI;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemDAOTest {

	@Mock
	private UI ui;
	
	@Mock
	private Connection mockConn;
	
	@Mock
	private Statement mockStatement;
	
	@Mock
	private PreparedStatement mockPrepStatement;
	
	@Mock
	private ResultSet mockRS;
	
	@InjectMocks
	private OrderItemDAO dao;
	
	@Test
	public void modelFromResultSetTest() throws SQLException {
		Mockito.when(mockRS.getLong("id")).thenReturn(1L);
		Mockito.when(mockRS.getLong("order_id")).thenReturn(1L);
		Mockito.when(mockRS.getString("item_name")).thenReturn("Titanic");
		Mockito.when(mockRS.getLong("quantity")).thenReturn(1L);
		
		assertEquals(new OrderItem(1L, 1L, "Titanic", 1L), dao.modelFromResultSet(mockRS));
		
		Mockito.verify(mockRS, Mockito.times(1)).getLong("id");
		Mockito.verify(mockRS, Mockito.times(1)).getLong("order_id");
		Mockito.verify(mockRS, Mockito.times(1)).getString("item_name");
		Mockito.verify(mockRS, Mockito.times(1)).getLong("quantity");
	}
	
	@Test
	public void modelSpecificOrderTest() throws SQLException {
		Mockito.when(mockRS.getLong("id")).thenReturn(1L);
		Mockito.when(mockRS.getString("item_name")).thenReturn("Titanic");
		Mockito.when(mockRS.getLong("quantity")).thenReturn(1L);
		
		assertEquals(new OrderItem(1L, "Titanic", 1L), dao.modelForSpecificOrder(mockRS));
		
		Mockito.verify(mockRS, Mockito.times(1)).getLong("id");
		Mockito.verify(mockRS, Mockito.times(1)).getString("item_name");
		Mockito.verify(mockRS, Mockito.times(1)).getLong("quantity");
	}
	
	@Test
	public void testCreate() throws SQLException {
		final OrderItem oItemA = new OrderItem(1L, 1L, 1L);
		OrderItemDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		Mockito.doReturn(oItemA).when(spy).readLatest();
		
		assertEquals(oItemA, spy.create(oItemA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(spy, Mockito.times(1)).readLatest();
	}
	
	@Test
	public void testCreateException() throws SQLException {
		final OrderItem oItemA = new OrderItem(1L, 1L, 1L);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.create(oItemA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}

	@Test
	public void testReadLatest() throws SQLException {
		final OrderItem oItemA = new OrderItem(1L, "Titanic", 1L);
		OrderItemDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(oItemA).when(spy).modelForSpecificOrder(mockRS);
		
		assertEquals(oItemA, spy.readLatest());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(1)).next();
		Mockito.verify(spy, Mockito.times(1)).modelForSpecificOrder(mockRS);
	}
	
	@Test
	public void testReadLatestException() throws SQLException {
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenThrow(SQLException.class);
		
		assertEquals(null, dao.readLatest());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testReadAll() throws SQLException {
		List<OrderItem> oItems = new ArrayList<>();
		final OrderItem oItemA = new OrderItem(1L, "Titanic", 1L);
		oItems.add(oItemA);
		OrderItemDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true, false);
		Mockito.doReturn(oItemA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(oItems, spy.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(2)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
	}
	
	@Test
	public void testReadAllException() throws SQLException {
		List<OrderItem> oItems = new ArrayList<>();
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenThrow(SQLException.class);
		
		assertEquals(oItems, dao.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}

	@Test
	public void testReadAllSpecificID() throws SQLException {
		List<OrderItem> oItems = new ArrayList<>();
		final OrderItem oItemA = new OrderItem(1L, "Titanic", 1L);
		oItems.add(oItemA);
		OrderItemDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true, false);
		Mockito.doReturn(oItemA).when(spy).modelForSpecificOrder(mockRS);
		
		assertEquals(oItems, spy.readAll(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(2)).next();
		Mockito.verify(spy, Mockito.times(1)).modelForSpecificOrder(mockRS);
	}	
	
	@Test
	public void testReadAllSpecificIDException() throws SQLException {
		List<OrderItem> oItems = new ArrayList<>();

		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenThrow(SQLException.class);
		
		assertEquals(oItems, dao.readAll(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}	
	
	@Test
	public void testDelete() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		
		assertEquals(1, dao.delete(1L, 1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testDeleteNotFoundException() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(0);
		
		assertEquals(0, dao.delete(1L, 1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testDeleteException() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(0, dao.delete(1L, 1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
}

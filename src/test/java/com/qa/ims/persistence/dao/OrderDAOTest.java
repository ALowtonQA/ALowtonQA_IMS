package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.UI;

@RunWith(MockitoJUnitRunner.class)
public class OrderDAOTest {

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
	private OrderDAO dao;
	
	@Test
	public void modelFromResultSetTest() throws SQLException {
		Timestamp ts = new Timestamp(99999);
		Date date = new Date(ts.getTime());
		Mockito.when(mockRS.getLong("id")).thenReturn(1L);
		Mockito.when(mockRS.getString("customer")).thenReturn("chris perrins");
		Mockito.when(mockRS.getTimestamp("date")).thenReturn(ts);
		
		assertEquals(new Order(1L, "chris perrins", date), dao.modelFromResultSet(mockRS));
		
		Mockito.verify(mockRS, Mockito.times(1)).getLong("id");
		Mockito.verify(mockRS, Mockito.times(1)).getString("customer");
		Mockito.verify(mockRS, Mockito.times(1)).getTimestamp("date");
	}
	
	@Test
	public void modelTotalCostTest() throws SQLException {
		Timestamp ts = new Timestamp(99999);
		Date date = new Date(ts.getTime());
		Mockito.when(mockRS.getLong("id")).thenReturn(1L);
		Mockito.when(mockRS.getString("customer")).thenReturn("chris perrins");
		Mockito.when(mockRS.getTimestamp("date")).thenReturn(ts);
		Mockito.when(mockRS.getDouble("total_price")).thenReturn(9.99);
		
		assertEquals(new Order(1L, "chris perrins", date, 9.99), dao.modelTotalCost(mockRS));
		
		Mockito.verify(mockRS, Mockito.times(1)).getLong("id");
		Mockito.verify(mockRS, Mockito.times(1)).getString("customer");
		Mockito.verify(mockRS, Mockito.times(1)).getTimestamp("date");
		Mockito.verify(mockRS, Mockito.times(1)).getDouble("total_price");
	}
	
	@Test
	public void testCreate() throws SQLException {
		final Order orderA = new Order(1L);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		Mockito.doReturn(orderA).when(spy).readLatest();
		
		assertEquals(orderA, spy.create(orderA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(spy, Mockito.times(1)).readLatest();
	}
	
	@Test
	public void testCreateException() throws SQLException {
		final Order orderA = new Order(1L);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.create(orderA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testRead() throws SQLException {
		final Order orderA = new Order(1L);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.isBeforeFirst()).thenReturn(true);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(orderA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(orderA, spy.read(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(1)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(1)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
	}
	
	@Test
	public void testReadNotFoundException() throws SQLException {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.isBeforeFirst()).thenReturn(false);
		
		assertEquals(null, dao.read(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(1)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testReadException() throws SQLException {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.read(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(0)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testReadLatest() throws SQLException {
		final Order orderA = new Order(1L);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(orderA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(orderA, spy.readLatest());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(1)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
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
		List<Order> orders = new ArrayList<>();
		final Order orderA = new Order(1L);
		orders.add(orderA);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true, false);
		Mockito.doReturn(orderA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(orders, spy.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(2)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
	}
	
	@Test
	public void testReadAllException() throws SQLException {
		List<Order> orders = new ArrayList<>();
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenThrow(SQLException.class);
		
		assertEquals(orders, dao.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testUpdate() throws SQLException {
		final Order orderA = new Order(1L, 1L);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		Mockito.doReturn(orderA).when(spy).read(orderA.getId());
		
		assertEquals(orderA, spy.update(orderA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(spy, Mockito.times(1)).read(orderA.getId());
	}
	
	@Test
	public void testUpdateException() throws SQLException {
		final Order orderA = new Order(1L, 1L);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.update(orderA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testDelete() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		
		assertEquals(1, dao.delete(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testDeleteNotFoundException() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(0);
		
		assertEquals(0, dao.delete(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testDeleteException() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(0, dao.delete(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}
	
	@Test
	public void testTotalCost() throws Exception {
		Timestamp ts = new Timestamp(99999);
		Date date = new Date(ts.getTime());
		Order orderA = new Order(1L, "chris perrins", date, 9.99);
		OrderDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.isBeforeFirst()).thenReturn(true);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(orderA).when(spy).modelTotalCost(mockRS);
		
		assertEquals(orderA, spy.totalCost(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(1)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(1)).next();
		Mockito.verify(spy, Mockito.times(1)).modelTotalCost(mockRS);
	}
	
	@Test
	public void testTotalCostNull() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.isBeforeFirst()).thenReturn(false);
		
		assertEquals(null, dao.totalCost(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(1)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testTotalCostException() throws Exception {
Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.totalCost(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(0)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
}

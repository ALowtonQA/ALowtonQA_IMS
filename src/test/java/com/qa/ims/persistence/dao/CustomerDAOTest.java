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
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.UI;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDAOTest {

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
	private CustomerDAO dao;

	@Test
	public void modelFromResultSetTest() throws SQLException {
		Mockito.when(mockRS.getLong("id")).thenReturn(1L);
		Mockito.when(mockRS.getString("first_name")).thenReturn("chris");
		Mockito.when(mockRS.getString("surname")).thenReturn("perrins");
		
		assertEquals(new Customer(1L, "chris", "perrins"), dao.modelFromResultSet(mockRS));
		
		Mockito.verify(mockRS, Mockito.times(1)).getLong("id");
		Mockito.verify(mockRS, Mockito.times(1)).getString("first_name");
		Mockito.verify(mockRS, Mockito.times(1)).getString("surname");
	}
	
	@Test
	public void testCreate() throws SQLException {
		final Customer custA = new Customer(1L, "chris", "perrins");
		CustomerDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		Mockito.doReturn(custA).when(spy).readLatest();
		
		assertEquals(custA, spy.create(custA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(spy, Mockito.times(1)).readLatest();
	}

	@Test
	public void testCreateException() throws Exception {
		final Customer custA = new Customer(1L, "chris", "perrins");

		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.create(custA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
	}

	@Test
	public void testReadAll() throws SQLException {
		List<Customer> customers = new ArrayList<>();
		final Customer custA = new Customer(1L, "chris", "perrins");
		customers.add(custA);
		CustomerDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true, false);
		Mockito.doReturn(custA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(customers, spy.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(2)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
	}
	
	@Test
	public void testReadAllException() throws SQLException {
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenThrow(SQLException.class);
		
		assertEquals(new ArrayList<>(), dao.readAll());
		
		Mockito.verify(mockConn, Mockito.times(1)).createStatement();
		Mockito.verify(mockStatement, Mockito.times(1)).executeQuery(Mockito.any());
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testReadLatest() throws SQLException {
		final Customer custA = new Customer(1L, "chris", "perrins");
		CustomerDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.createStatement()).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery(Mockito.any())).thenReturn(mockRS);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(custA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(custA, spy.readLatest());
		
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
	public void testRead() throws SQLException {
		final Customer custA = new Customer(1L, "chris", "perrins");
		CustomerDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenReturn(mockRS);
		Mockito.when(mockRS.isBeforeFirst()).thenReturn(true);
		Mockito.when(mockRS.next()).thenReturn(true);
		Mockito.doReturn(custA).when(spy).modelFromResultSet(mockRS);
		
		assertEquals(custA, spy.read(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(1)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(1)).next();
		Mockito.verify(spy, Mockito.times(1)).modelFromResultSet(mockRS);
	}
	
	@Test
	public void testReadNotFoundException() throws Exception {
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
	public void testReadException() throws Exception {
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeQuery()).thenThrow(SQLException.class);

		assertEquals(null, dao.read(1L));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeQuery();
		Mockito.verify(mockRS, Mockito.times(0)).isBeforeFirst();
		Mockito.verify(mockRS, Mockito.times(0)).next();
	}
	
	@Test
	public void testUpdate() throws SQLException {
		final Customer custA = new Customer(1L, "chris", "perrins");
		CustomerDAO spy = Mockito.spy(dao);
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenReturn(1);
		Mockito.doReturn(custA).when(spy).read(custA.getId());
		
		assertEquals(custA, spy.update(custA));
		
		Mockito.verify(mockConn, Mockito.times(1)).prepareStatement(Mockito.any());
		Mockito.verify(mockPrepStatement, Mockito.times(1)).executeUpdate();
		Mockito.verify(spy, Mockito.times(1)).read(custA.getId());
	}
	
	@Test
	public void testUpdateException() throws SQLException {
		final Customer custA = new Customer(1L, "chris", "perrins");
		
		Mockito.when(mockConn.prepareStatement(Mockito.any())).thenReturn(mockPrepStatement);
		Mockito.when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);
		
		assertEquals(null, dao.update(custA));
		
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
}

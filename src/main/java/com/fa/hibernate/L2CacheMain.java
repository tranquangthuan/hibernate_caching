package com.fa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fa.hibernate.entity.Employee;
import com.fa.hibernate.utils.HibernateUtil;

public class L2CacheMain {

	public static void main(String[] args) {
		L2CacheMain cachingMain = new L2CacheMain();

		// cachingMain.differenceSession();
		// cachingMain.clearSession();
		cachingMain.clearRegion();
	}

	public void differenceSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session sessionA = factory.openSession();
		Transaction txA = null;
		Session sessionB = factory.openSession();
		Transaction txB = null;
		try {
			System.out.println("----Session A----");
			txA = sessionA.beginTransaction();
			Employee employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			factory.getCache().evictEntityRegion(Employee.class);

			System.out.println("----Session B----");
			txB = sessionB.beginTransaction();
			employee = (Employee) sessionB.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			txA.commit();
			txB.commit();
		} catch (HibernateException e) {
			if (txA != null)
				txA.rollback();
			if (txB != null)
				txB.rollback();
			e.printStackTrace();
		} finally {
			sessionA.close();
			sessionB.close();
		}
	}

	public void clearSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session sessionA = factory.openSession();
		Transaction txA = null;
		Session sessionB = factory.openSession();
		Transaction txB = null;
		try {
			System.out.println("----Session A----");
			txA = sessionA.beginTransaction();
			Employee employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			sessionA.evict(employee);
			sessionA.clear();
			sessionB.evict(employee);
			sessionB.clear();

			System.out.println("----Session B----");
			txB = sessionB.beginTransaction();
			employee = (Employee) sessionB.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			txA.commit();
			txB.commit();
		} catch (HibernateException e) {
			if (txA != null)
				txA.rollback();
			if (txB != null)
				txB.rollback();
			e.printStackTrace();
		} finally {
			sessionA.close();
			sessionB.close();
		}
	}

	public void clearRegion() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session sessionA = factory.openSession();
		Transaction txA = null;
		Session sessionB = factory.openSession();
		Transaction txB = null;
		try {
			System.out.println("----Session A----");
			txA = sessionA.beginTransaction();
			Employee employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			employee = (Employee) sessionA.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			factory.getCache().evictEntityRegion(Employee.class);

			System.out.println("----Session B----");
			txB = sessionB.beginTransaction();
			employee = (Employee) sessionB.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			txA.commit();
			txB.commit();
		} catch (HibernateException e) {
			if (txA != null)
				txA.rollback();
			if (txB != null)
				txB.rollback();
			e.printStackTrace();
		} finally {
			sessionA.close();
			sessionB.close();
		}
	}

}

package com.fa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fa.hibernate.entity.Employee;
import com.fa.hibernate.utils.HibernateUtil;

public class L1CacheMain {

	public static void main(String[] args) {
		L1CacheMain cachingMain = new L1CacheMain();
		cachingMain.sameSession();
		// cachingMain.differenceSession();
		// cachingMain.deleteSession();
	}

	public void sameSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			employee = (Employee) session.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			for (int i = 0; i < 5; i++) {
				employee = (Employee) session.get(Employee.class, 1);
				System.out.println(employee.getFirstName());
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
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

	public void deleteSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			employee = (Employee) session.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			session.evict(employee);
			System.out.println("After delete session");

			employee = (Employee) session.get(Employee.class, 1);
			System.out.println(employee.getFirstName());

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}

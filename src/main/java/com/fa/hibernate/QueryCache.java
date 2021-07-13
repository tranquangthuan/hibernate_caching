package com.fa.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fa.hibernate.entity.Employee;
import com.fa.hibernate.utils.HibernateUtil;

public class QueryCache {

	public static void main(String[] args) {
		QueryCache cachingMain = new QueryCache();

		cachingMain.sameSession();
	}

	public void sameSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String hql = "FROM Employee emp WHERE emp.id = :id";
			Query query = session.createQuery(hql);
			query.setCacheable(true);
			// query.setCacheRegion(Employee.class.getCanonicalName());
			Employee employee = (Employee) query.setParameter("id", 1).uniqueResult();
			System.out.println(employee.getFirstName());

			employee = (Employee) query.setParameter("id", 1).uniqueResult();
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

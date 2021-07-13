package com.fa.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fa.hibernate.entity.Employee;
import com.fa.hibernate.utils.HibernateUtil;

public class ManageEmployee {
	private static SessionFactory factory;

	public static void main(String[] args) {
		// get session factory
		factory = HibernateUtil.getSessionFactory();

		// create manageEmployee object
		ManageEmployee manageEmployee = new ManageEmployee();

		// Add few employee records in database
//		Integer empID1 = manageEmployee.addEmployee("David", "Bishop", 1000);
//		Integer empID2 = manageEmployee.addEmployee("Chris", "Ali", 5000);
//		Integer empID3 = manageEmployee.addEmployee("John", "Vector", 10000);

		manageEmployee.listEmployees();
	}

	// Method to CREATE an employee in the database
	public Integer addEmployee(String fname, String lname, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Employee employee = new Employee(fname, lname, salary);
			employeeID = (Integer) session.save(employee);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employeeID;
	}

	// Method to READ all the employees
	public void listEmployees() {
		Session session = factory.openSession();
		try {
			@SuppressWarnings("unchecked")
			List<Employee> employees = session.createQuery("FROM Employee").list();
			for (Employee employee : employees) {
				// Employee employee = (Employee) iterator.next();
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print("  Last Name: " + employee.getLastName());
				System.out.println("  Salary: " + employee.getSalary());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// Method to UPDATE salary for an employee
	public void updateEmployee(Integer EmployeeID, int salary) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Employee employee = (Employee) session.get(Employee.class, EmployeeID);
			employee.setSalary(salary);
			session.update(employee);
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
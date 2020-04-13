package Test;

import fachlogik.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestUntersuchungDB {
	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("jpaUntersuchungen");
		EntityManager em = emfactory.createEntityManager();
//		em.getTransaction().begin();
//
		/* ObservableList<Patient> patientData = FXCollections.observableArrayList();
		javafx.collections.ObservableList<Personal> personData = FXCollections.observableArrayList();
		ObservableList<Untersuchung> examinationData = FXCollections.observableArrayList();

*/
		List<Patient> patientData = new ArrayList<>();
		List<Personal> personData = new ArrayList<>();
		List<Untersuchung> examinationData = new ArrayList<>();


		// test data
		patientData.add(new Patient(1234, "01.01.1980", "Thomas", "Maier", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 13", "WGKK"));
		patientData.add(new Patient(4321, "01.01.1980", "Margit", "Schmidt", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 27", "BVA"));
		personData.add(new Personal(5678, "01.01.1980", "Hans", "Lang", Geschlecht.MAENNLICH, "1010 Wien, Stephansplatz 1", 0));
		personData.add(new Personal(8765, "01.01.1980", "Ruth", "Kurz", Geschlecht.WEIBLICH, "1050 Wien, Spengergasse 20", 0));


		em.getTransaction().begin();
		Ultraschall u = new Ultraschall();
		u.setPatient(patientData.get(0));
		//u.setBeginn(LocalDateTime.of(2017,11,21,7,15));
		//u.setEnde(LocalDateTime.of(2017,11,21,7,25));
		u.setBeginn("2017-11-21T07:15:00");
		u.setEnde("2017-11-21T07:25:00");
		examinationData.add(u);
		em.persist(u);


		MagnetResonanz m1 = new MagnetResonanz();
		m1.setPatient(patientData.get(1));
		m1.setKm("Artirem Injektionslösung");
		m1.setMengeKM("3");
		m1.setBeginn("2017-11-21T10:23:00");
		m1.setEnde("2017-11-21T10:51:00");
		examinationData.add(m1);
		em.persist(m1);

		for (Patient p: patientData
			 ) {
			em.persist(p);
		}



//		Department d1 = new Department();
//		d1.setName("IT");
//		d1.setLocation("Vienna");
//
//		Department d2 = new Department();
//		d2.setName("Marketing");
//		d2.setLocation("Vienna");
//
//		Employee e1 = new Employee();
//		e1.setName("Maier");
//		e1.setBirth("2000-02-03");
//		e1.setDepartment(d1);
//		em.persist(e1);
//
//		Employee e2 = new Employee();
//		e2.setName("Kurz");
//		e2.setBirth("2004-12-31");
//		e2.setDepartment(d1);
//		em.persist(e2);
//
//		Employee e3 = new Employee();
//		e3.setName("Lang");
//		e3.setBirth("2002-10-23");
//		e3.setDepartment(d1);
//		em.persist(e3);
//
//		Employee e4 = new Employee();
//		e4.setName("Schmidt");
//		e4.setBirth("1999-05-15");
//		e4.setDepartment(d2);
//		em.persist(e4);
//
//		d1.setManager(e2);
//		em.persist(d1);
//
//		d2.setManager(e4);
//		em.persist(d2);
//
//		Project p1 = new Project();
//		p1.setName("Application A");
//		p1.setBegin("2014-03-09");
//		p1.setEnd("2016-09-03");
//		p1.setMembers(new ArrayList<Employee>(Arrays.asList(e1,e2)));
//		em.persist(p1);
//
//		Project p2 = new Project();
//		p2.setName("Application B");
//		p2.setBegin("2017-06-06");
//		p2.setMembers(new ArrayList<Employee>(Arrays.asList(e2,e3)));
//		em.persist(p2);
//
//		Project p3 = new Project();
//		p3.setName("Application C");
//		p3.setBegin("2015-08-01");
//		p3.setEnd("2017-04-13");
//		p3.setMembers(new ArrayList<Employee>(Arrays.asList(e1,e3)));
//		em.persist(p3);
//
//		Project p4 = new Project();
//		p4.setName("Campaign 2018");
//		p4.setBegin("2018-01-01");
//		p4.setMembers(new ArrayList<Employee>(Arrays.asList(e4)));
//		em.persist(p4);
//
//		em.getTransaction().commit();

/*		Untersuchung untersuchung = em.find(Untersuchung.class, 1);
		System.out.println(untersuchung);
		untersuchung.getPersonal().forEach(e -> System.out.println(e));*/
		
		TypedQuery<Untersuchung> query1 = em.createNamedQuery("Untersuchung.findAll", Untersuchung.class);
		List<Untersuchung> unterList = query1.getResultList();
		unterList.forEach(d -> System.out.println(d));
		
	/*	TypedQuery<Project> query2 = em.createNamedQuery("Project.byName", Project.class);
		query2.setParameter("name", "A%");
		query2.getResultList().forEach(p -> System.out.println(p));
		
		TypedQuery<Employee> query3 = em.createNamedQuery("Employee.byBirth", Employee.class);
		query3.setParameter("dateFrom", LocalDate.of(1998, 1, 1).toString());
		query3.setParameter("dateTo", LocalDate.of(2002, 1, 1).toString());
		query3.getResultList().forEach(e -> System.out.println(e));
		
		// Save employee with StringProperty description:
		em.getTransaction().begin();
		Employee e = new Employee();
		e.setName("Huber");
		e.setDescription("Oldest Employee");
		e.setBirth("1970-11-22");
		e.setDepartment(dpt);
		em.persist(e);
		em.getTransaction().commit();*/

		em.getTransaction().commit();
		em.close();
		emfactory.close();
	}

}

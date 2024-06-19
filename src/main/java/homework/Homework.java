package homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Перенести структуру дз третьего урока на JPA:
 * 1. Описать сущности Student и Group
 * 2. Написать запросы: Find, Persist, Remove
 * 3. ... поупражняться с разными запросами ...
 */
public class Homework {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "root", "root")) {
            run(connection);
        }
    }

    private static void run(Connection connection) throws SQLException {
        Configuration configuration = new Configuration().configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                Student student1 = new Student();
                student1.setId(1L);
                student1.setFirstName("Ivan");
                student1.setLastName("Ivanovich");
                student1.setGroupId(1L);

                Student student2 = new Student();
                student2.setId(2L);
                student2.setFirstName("Alexei");
                student2.setLastName("Alexeev");
                student2.setGroupId(1L);

                //Persist
                session.beginTransaction();
                session.persist(student1);
                session.persist(student2);

                //Find/Select
                System.out.println(findStudentById(session, 1L));
                System.out.println(findStudentById(session, 2L));

                //Remove
                session.remove(student1);
                System.out.println(findStudentById(session, 1L));
            }
        }
    }

    private static Student findStudentById(Session session, Long id) {
        return session.find(Student.class, id);
    }


}

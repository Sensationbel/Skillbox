
import entity.Course;
import entity.LinkedPurchaseList;
import entity.PurchaseList;
import entity.Student;
import enumAndKey.Key;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query qp = session.createQuery("FROM PurchaseList");
        List<PurchaseList> pl = qp.getResultList();
        addDataToLinkedPurchaseList(session, pl);
        session.close();
        HibernateUtil.shutdown();
    }

    private static void addDataToLinkedPurchaseList(Session session, List<PurchaseList> pl) {
        pl.forEach(purchaseList -> {
            String studentName = purchaseList.getKey().getStudentName();
            String courseName = purchaseList.getKey().getCourseName();
            Integer studentId = getStudentId(session, studentName);
            Integer courseId = getCourseId(session, courseName);
            Transaction transaction = session.beginTransaction();
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList(new Key(studentId, courseId),studentId, courseId);
            session.saveOrUpdate(linkedPurchaseList);
            transaction.commit();
        });
    }

    private static Integer getCourseId(Session session, String courseName) {
        Query<Course>qc = session.createQuery("from Course where name = :text");
        qc.setParameter("text", courseName);
        Course course = qc.uniqueResult();
        return course.getId();
    }

    private static Integer getStudentId(Session session, String studentName) {
        Query<Student> qs = session.createQuery("from Student where name = : text");
        qs.setParameter("text", studentName);
        Student students = qs.uniqueResult();
        return students.getId();
    }
}
package dao.hibernate;

import dao.AbstractDeveloperDao;
import model.Developer;
import model.Skill;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class HibernateDeveloperDao implements AbstractDeveloperDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateDeveloperDao.class);
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<Developer> getAll() {
        LOGGER.info("Retrieving list of Developer (Hibernate)");
        return sessionFactory.getCurrentSession().createQuery("SELECT e FROM Developer e").list();
    }

    @Override
    @Transactional
    public List<Developer> read(String firstName) {
        LOGGER.info("Retrieving list of Developer by name (Hibernate)");
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Developer D WHERE D.firstName = :first_name" );
        query.setParameter("first_name", firstName);
        return query.list();
    }

    @Override
    @Transactional
    public List<Developer> read(String firstName, String lastName) {
        LOGGER.info("Retrieving list of Developer by first and last names (Hibernate)");
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Developer D WHERE D.firstName = :first_name AND D.lastName = :last_name");
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.list();
    }

    @Override
    @Transactional
    public boolean delete(Developer developer) {
        sessionFactory.getCurrentSession().delete(developer);
        LOGGER.info("Deleting Developer (Hibernate)");
        return true;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        Developer developer = sessionFactory.getCurrentSession().load(Developer.class, id);
        sessionFactory.getCurrentSession().delete(developer);
        LOGGER.info("Deleting Developer by id (Hibernate)");
        return true;
    }

    @Override
    @Transactional
    public Developer updateSalary(Developer developer, int newSalary) {
        Query query = sessionFactory.getCurrentSession().createQuery("UPDATE Developer D SET salary = :salary WHERE D.id = :developer_id");
        query.setParameter("salary", newSalary);
        query.setParameter("developer_id", developer.getId());
        query.executeUpdate();
        LOGGER.info("Updating Salary for Developer " +developer.getId() + "(Hibernate)");
        return sessionFactory.getCurrentSession().load(Developer.class, developer.getId());
    }

    @Override
    @Transactional
    public Developer updateSkill(Developer developer, String skill) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Skill S where S.name = :name");
        query.setParameter("name", skill);
        Skill newSkill = (Skill) query.list().get(0);

        developer.getSkills().add(newSkill);

        sessionFactory.getCurrentSession().update("Developer",developer);

        LOGGER.info("Updating Skill for Developer " +developer.getId() + "(Hibernate)");
        return sessionFactory.getCurrentSession().load(Developer.class, developer.getId());
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

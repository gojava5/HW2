import dao.hibernate.HibernateDeveloperDao;
import dao.jdbc.JdbcDeveloperDao;
import model.Developer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class Main {

    private JdbcDeveloperDao jdbcDeveloperDao;
    private HibernateDeveloperDao hibernateDeveloperDao;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml", "hibernate-context.xml");
        Main main = context.getBean(Main.class);

//        main.getAll();
//        System.out.println("----------------------");
//        main.getDeveloper("Han");
//        System.out.println("----------------------");
//        main.getDeveloper("Bernard", "Lowe");
//        System.out.println("----------------------");
//        main.deleteDeveloper("Illia");

//        main.deleteDeveloper();
//        main.deleteDeveloperbyId();
//        main.getAllHib();
//        main.getDeveloperHib();
//        main.updateSalaryHib();
        main.updateSkillHib();
    }

    private void updateSkillHib() {
        Developer developer = hibernateDeveloperDao.read("John").get(0);
        developer = hibernateDeveloperDao.updateSkill(developer, "Java");
        System.out.println(developer);
    }

    private void updateSalaryHib() {
        Developer developer = hibernateDeveloperDao.read("Peter").get(0);
        hibernateDeveloperDao.updateSalary(developer, 9500);
        developer = hibernateDeveloperDao.read("Peter").get(0);
        System.out.println(developer);
    }


    private void getDeveloperHib() {
        List list = hibernateDeveloperDao.read("John", "Snow");
        list.forEach(System.out::println);
    }

    private void getAllHib() {
        List list = hibernateDeveloperDao.getAll();
        list.forEach(System.out::println);
    }

    private void deleteDeveloperbyId() {
        Developer developer = jdbcDeveloperDao.read("Illia").get(0);
        int id = developer.getId();
        hibernateDeveloperDao.delete(id);

    }

    private void deleteDeveloper() {
        Developer developer = jdbcDeveloperDao.read("Jimmy").get(0);
        hibernateDeveloperDao.delete(developer);
    }

    private void deleteDeveloper(String name) {
        Developer developer = jdbcDeveloperDao.read(name).get(0);
        System.out.println(jdbcDeveloperDao.delete(developer));
    }

    private Developer setDeveloper(String name) {
        return jdbcDeveloperDao.read(name).get(0);
    }

    private void updateSkil(Developer developer, String skill) {
        System.out.println(jdbcDeveloperDao.updateSkill(developer, skill));
    }

    private void getDeveloper(String firstName, String lastName) {
        jdbcDeveloperDao.read(firstName,lastName).forEach(System.out::println);
    }

    private void getDeveloper(String firstName) {
        jdbcDeveloperDao.read(firstName).forEach(System.out::println);
    }

    private void getAll () {
        jdbcDeveloperDao.getAll().forEach(System.out::println);
    }

    public void setJdbcDeveloperDao(JdbcDeveloperDao jdbcDeveloperDao) {
        this.jdbcDeveloperDao = jdbcDeveloperDao;
    }

    public void setHibernateDeveloperDao(HibernateDeveloperDao hibernateDeveloperDao) {
        this.hibernateDeveloperDao = hibernateDeveloperDao;
    }
}

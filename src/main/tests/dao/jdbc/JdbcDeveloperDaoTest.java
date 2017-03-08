package dao.jdbc;

import model.Developer;
import model.Skill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"file:src/main/resources/application-context.xml"})
public class JdbcDeveloperDaoTest {

    @Autowired
    private JdbcDeveloperDao jdbcDeveloperDao;


    @Test
    public void getAll() throws Exception {
        jdbcDeveloperDao.getAll().forEach(System.out::println);
        Assert.assertNotNull(jdbcDeveloperDao.getAll());
    }

    @Test
    public void read() throws Exception {
        jdbcDeveloperDao.read("Peter").forEach(System.out::println);
        Assert.assertNotNull(jdbcDeveloperDao.read("Peter"));
    }

    @Test
    public void read1() throws Exception {
        jdbcDeveloperDao.read("Bernard", "Lowe").forEach(System.out::println);
        Assert.assertNotNull(jdbcDeveloperDao.read("Bernard", "Lowe"));
    }

    @Test
    public void delete() throws Exception {
        //        Type name of Developer to make test
        String name = "Jimmy";
        Developer testDeveloper = jdbcDeveloperDao.read(name).get(0);
        System.out.println("Initial list of Developers: ");
        jdbcDeveloperDao.getAll().forEach(System.out::println);
        int id = testDeveloper.getId();
        Assert.assertTrue(jdbcDeveloperDao.delete(id));
        System.out.println("List of Developers after deletion: ");
        jdbcDeveloperDao.getAll().forEach(System.out::println);
        System.out.println("--------------------");
        System.out.println("Looking for " +name +" : ");
        jdbcDeveloperDao.read(name).forEach(System.out::println);
    }

    @Test
    public void delete1() throws Exception {
//        Type name of Developer to make test
        String name = "Illia";
        Developer testDeveloper = jdbcDeveloperDao.read(name).get(0);
        System.out.println("Initial list of Developers: ");
        jdbcDeveloperDao.getAll().forEach(System.out::println);
        Assert.assertTrue(jdbcDeveloperDao.delete(testDeveloper));
        System.out.println("List of Developers after deletion: ");
        jdbcDeveloperDao.getAll().forEach(System.out::println);
        System.out.println("--------------------");
        System.out.println("Looking for " +name +" : ");
        jdbcDeveloperDao.read(name).forEach(System.out::println);
    }

    @Test
    public void updateSalary() throws Exception {

        Developer testDeveloper = jdbcDeveloperDao.read("Han").get(0);
        System.out.println("Starting salary: " + testDeveloper.getSalary());
        jdbcDeveloperDao.updateSalary(testDeveloper,9000);
        testDeveloper.setSalary(9000);
        System.out.printf("New salary: " + testDeveloper.getSalary());
        Assert.assertEquals(testDeveloper,jdbcDeveloperDao.read("Han").get(0));



    }

    @Test
    public void updateSkill() throws Exception {
        Developer testDeveloper = jdbcDeveloperDao.read("Han").get(0);
        Skill testSkill = new Skill(4,"CSS");

        Developer resultDeveloper = jdbcDeveloperDao.updateSkill(testDeveloper,"CSS");
        Set <Skill> skills = testDeveloper.getSkills();
        System.out.println("Starting skills: " + skills);
        skills.add(testSkill);
        testDeveloper.setSkills(skills);
        System.out.println("Updated skills: " + skills);
        Assert.assertEquals(testDeveloper, resultDeveloper);
        Assert.assertEquals(testDeveloper, jdbcDeveloperDao.read("Han").get(0));

    }

}
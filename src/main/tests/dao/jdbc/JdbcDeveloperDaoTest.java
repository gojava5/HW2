package dao.jdbc;

import model.Developer;
import model.Skill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"file:src/main/resources/application-context.xml"})
public class JdbcDeveloperDaoTest {

    @Autowired
    private JdbcDeveloperDao jdbcDeveloperDao;


    @Test
    public void getAll() throws Exception {
        Assert.assertNotNull(jdbcDeveloperDao.getAll());
    }

    @Test
    public void read() throws Exception {
        Assert.assertNotNull(jdbcDeveloperDao.read("Peter"));
    }

    @Test
    public void read1() throws Exception {
        Assert.assertNotNull(jdbcDeveloperDao.read("Bernard", "Lowe"));
    }

    @Test
    public void delete() throws Exception {
        //        Type name of Developer to make test
        String name = "Jimmy";
        Developer testDeveloper = jdbcDeveloperDao.read(name).get(0);
        List initialDevelopers = jdbcDeveloperDao.getAll();
        initialDevelopers.remove(testDeveloper);
        int id = testDeveloper.getId();
        Assert.assertTrue(jdbcDeveloperDao.delete(id));
        List finalList = jdbcDeveloperDao.getAll();
        Assert.assertEquals(initialDevelopers, finalList);
    }

    @Test
    public void delete1() throws Exception {
//        Type name of Developer to make test
        String name = "Illia";
        Developer testDeveloper = jdbcDeveloperDao.read(name).get(0);
        List initialDevelopers = jdbcDeveloperDao.getAll();
        Assert.assertTrue(jdbcDeveloperDao.delete(testDeveloper));
        List finalList = jdbcDeveloperDao.getAll();
        Assert.assertEquals(initialDevelopers, finalList);
    }

    @Test
    public void updateSalary() throws Exception {

        Developer testDeveloper = jdbcDeveloperDao.read("Han").get(0);
        jdbcDeveloperDao.updateSalary(testDeveloper,9000);
        testDeveloper.setSalary(9000);
        Assert.assertEquals(testDeveloper,jdbcDeveloperDao.read("Han").get(0));
    }

    @Test
    public void updateSkill() throws Exception {
        Developer testDeveloper = jdbcDeveloperDao.read("Han").get(0);
        Skill testSkill = new Skill(4,"CSS");

        Developer resultDeveloper = jdbcDeveloperDao.updateSkill(testDeveloper,"CSS");
        Set <Skill> skills = testDeveloper.getSkills();
        skills.add(testSkill);
        testDeveloper.setSkills(skills);
        Assert.assertEquals(testDeveloper, resultDeveloper);
        Assert.assertEquals(testDeveloper, jdbcDeveloperDao.read("Han").get(0));

    }

}
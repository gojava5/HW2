package dao.hibernate;

import model.Developer;
import model.Skill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/application-context.xml","file:src/main/resources/hibernate-context.xml" })

public class HibernateDeveloperDaoTest {

    @Autowired
    private HibernateDeveloperDao hibernateDeveloperDao;


    @Test
    public void getAll() throws Exception {
        Assert.assertNotNull(hibernateDeveloperDao.getAll());
    }

    @Test
    public void read() throws Exception {
        Assert.assertNotNull(hibernateDeveloperDao.read("Peter"));

    }

    @Test
    public void read1() throws Exception {
        Assert.assertNotNull(hibernateDeveloperDao.read("Peter", "Parker"));

    }

    @Test
    public void delete() throws Exception {
        List initialList = hibernateDeveloperDao.getAll();
        Developer developer = hibernateDeveloperDao.read("Illia").get(0);
        Assert.assertTrue(hibernateDeveloperDao.delete(developer));
        List finalList = hibernateDeveloperDao.getAll();
        initialList.remove(developer);
        Assert.assertEquals(initialList, finalList);

    }

    @Test
    public void delete1() throws Exception {
        List initialList = hibernateDeveloperDao.getAll();
        Developer developer = hibernateDeveloperDao.read("Jimmy").get(0);
        Assert.assertTrue(hibernateDeveloperDao.delete(developer.getId()));
        List finalList = hibernateDeveloperDao.getAll();
        initialList.remove(developer);
        Assert.assertEquals(initialList, finalList);

    }

    @Test
    public void updateSalary() throws Exception {
        Developer developer = hibernateDeveloperDao.read("Han").get(0);
        int newSalary = 9100;
        hibernateDeveloperDao.updateSalary(developer, newSalary);
        developer.setSalary(newSalary);
        Assert.assertEquals(developer, hibernateDeveloperDao.read("Han").get(0));

    }

    @Test
    public void updateSkill() throws Exception {
        Developer developer = hibernateDeveloperDao.read("John").get(0);
        Skill testSkill = new Skill (2, "C++");
        hibernateDeveloperDao.updateSkill(developer, testSkill.getName());
        developer.getSkills().add(testSkill);
        Assert.assertEquals(developer, hibernateDeveloperDao.read("John").get(0));

    }

}
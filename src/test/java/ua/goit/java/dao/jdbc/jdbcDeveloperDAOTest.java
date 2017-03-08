package ua.goit.java.dao.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.goit.java.jdbc.model.Developer;
import ua.goit.java.jdbc.model.Skill;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by alexandrsemenov on 02.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/application-context.xml"} )
public class jdbcDeveloperDAOTest {

    @Autowired
    private jdbcDeveloperDAO developerDAO = new jdbcDeveloperDAO();


    @Test
    public void testDeleteById() throws Exception {
        org.junit.Assert.assertTrue(developerDAO.deleteById(1));
    }

    @Test
    public void testCreate() throws Exception {
        Developer developer = new Developer();
        developer.setId(77);
        developer.setName("Alex");
        developer.setPhone(38088888);
        developer.setSalary(new BigDecimal(55555));
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1, "Java"));
        developer.setSkills(skills);

        org.junit.Assert.assertEquals(developer, developerDAO.create(10, "Alex", 38088888, new BigDecimal(55555), skills));
    }
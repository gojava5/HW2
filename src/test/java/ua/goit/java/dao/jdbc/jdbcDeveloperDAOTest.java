package ua.goit.java.dao.jdbc;

import org.junit.Test;
import ua.goit.java.model.Developer;
import ua.goit.java.model.Skill;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by alexandrsemenov on 02.03.17.
 */
public class jdbcDeveloperDAOTest {

    @Test
    public void testDeleteById() throws Exception {
        jdbcDeveloperDAO developerDAO = new jdbcDeveloperDAO();
        org.junit.Assert.assertTrue(developerDAO.deleteById(1));
    }

    @Test
    public void testCreate() throws Exception {
        jdbcDeveloperDAO developerDAO = new jdbcDeveloperDAO();
        Developer developer = new Developer();
        developer.setId(77);
        developer.setName("Alex");
        developer.setPhone(38088888);
        developer.setSalary(new BigDecimal(55555));
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill());
        developer.setSkills(skills);

        org.junit.Assert.assertEquals(developer, developerDAO.create(10, "Alex", 38088888, new BigDecimal(55555), skills));
    }
}
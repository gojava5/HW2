package ua.goit.java.dao;

import ua.goit.java.model.Developer;
import ua.goit.java.model.Skill;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by alexandrsemenov on 26.02.17.
 */
public interface DeveloperDAO {
    Developer getById(int id);
    Developer create(int id, String name, int phone, BigDecimal salary, Collection<Skill> skills);
}

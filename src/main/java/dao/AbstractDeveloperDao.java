package dao;

import model.Developer;

import java.util.List;

public interface AbstractDeveloperDao {
    List<Developer> getAll ();
    List<Developer> read (String firstName);
    List<Developer> read (String firstName, String lastName);
    boolean delete (Developer developer);
    boolean delete (int id);
    Developer updateSalary (Developer developer, int newSalary);
    Developer updateSkill (Developer developer, String skill);

}

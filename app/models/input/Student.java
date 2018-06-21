package models.input;

import java.util.List;

public class Student
{
    private Integer       idStudent;
    private List<Classes> listClassees;


    public Student(Integer idStudent, List<Classes> listClassees)
    {
        this.listClassees = listClassees;
        this.idStudent = idStudent;
    }

    public List<Classes> getListClassees()
    {
        return listClassees;
    }

    public Integer getIdStudent()
    {
        return idStudent;
    }

}

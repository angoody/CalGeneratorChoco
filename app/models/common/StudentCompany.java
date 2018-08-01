package models.common;

import java.util.ArrayList;
import java.util.List;

public class StudentCompany
{
    private Integer       maxStudentInTraining = -1;
    private List<Student> listStudentCompany   = new ArrayList<>();

    public Integer getMaxStudentInTraining()
    {
        return maxStudentInTraining;
    }

    public void setMaxStudentInTraining(Integer maxStudentInTraining)
    {
        this.maxStudentInTraining = maxStudentInTraining;
    }

    public List<Student> getListStudentCompany()
    {
        return listStudentCompany;
    }

    public void setListStudentCompany(List<Student> listStudentCompany)
    {
        this.listStudentCompany = listStudentCompany;
    }
}

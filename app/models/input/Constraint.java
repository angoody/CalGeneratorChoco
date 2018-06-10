package models.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Constraint implements Serializable
{
    private ConstraintPriority<Integer>           place                          = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>           annualNumberOfHour             = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<Integer>           maxDurationOfTraining          = new ConstraintPriority<>(-1, -1);
    private ConstraintPriority<TrainingFrequency> trainingFrequency              = new ConstraintPriority<>(-1, new TrainingFrequency());
    private ConstraintPriority<StudentCompany>    maxStudentInTraining           = new ConstraintPriority<>(-1, new StudentCompany());
    private List<ConstraintPriority<Student>>     listStudentRequired            = new ArrayList<>();
    private List<ConstraintPriority<Period>>      listPeriodeOfTrainingExclusion = new ArrayList<>();
    private List<ConstraintPriority<Period>>      listPeriodeOfTrainingInclusion = new ArrayList<>();
    private ConstraintPriority<Boolean>           constraintPrerequisite         = new ConstraintPriority<Boolean>(-1, true);


    public Constraint()
    {

    }

    public Constraint(ConstraintPriority<Integer> constraintLieu, ConstraintPriority<Integer> constraintHeureAnnuel, ConstraintPriority<Integer> constraintDureeMaxFormation, ConstraintPriority<TrainingFrequency> constraintFrequenceFormation, ConstraintPriority<StudentCompany> constraintMaxStagiaireEnFormation, List<ConstraintPriority<Student>> constraintStagiairesRecquis, List<ConstraintPriority<Period>> constraintPeriodeFormationExclusion, List<ConstraintPriority<Period>> constraintPeriodeFormationInclusion, ConstraintPriority<Boolean> constraintPrerequis)
    {
        this.place = constraintLieu;
        this.annualNumberOfHour = constraintHeureAnnuel;
        this.maxDurationOfTraining = constraintDureeMaxFormation;
        this.trainingFrequency = constraintFrequenceFormation;
        this.maxStudentInTraining = constraintMaxStagiaireEnFormation;
        this.listStudentRequired = constraintStagiairesRecquis;
        this.listPeriodeOfTrainingExclusion = constraintPeriodeFormationExclusion;
        this.listPeriodeOfTrainingInclusion = constraintPeriodeFormationInclusion;
        this.constraintPrerequisite = constraintPrerequis;
    }


    public ConstraintPriority<Integer> getPlace()
    {
        return place;
    }

    public void setPlace(ConstraintPriority<Integer> place)
    {
        this.place = place;
    }

    public ConstraintPriority<Integer> getAnnualNumberOfHour()
    {
        return annualNumberOfHour;
    }

    public void setAnnualNumberOfHour(ConstraintPriority<Integer> annualNumberOfHour)
    {
        this.annualNumberOfHour = annualNumberOfHour;
    }


    public ConstraintPriority<Integer> getMaxDurationOfTraining()
    {
        return maxDurationOfTraining;
    }

    public void setMaxDurationOfTraining(ConstraintPriority<Integer> maxDurationOfTraining)
    {
        this.maxDurationOfTraining = maxDurationOfTraining;
    }


    public ConstraintPriority<TrainingFrequency> getTrainingFrequency()
    {
        return trainingFrequency;
    }

    public void setTrainingFrequency(ConstraintPriority<TrainingFrequency> trainingFrequency)
    {
        this.trainingFrequency = trainingFrequency;
    }


    public List<ConstraintPriority<Student>> getListStudentRequired()
    {
        return listStudentRequired;
    }

    public void setListStudentRequired(List<ConstraintPriority<Student>> listStudentRequired)
    {
        this.listStudentRequired = listStudentRequired;
    }

    public List<ConstraintPriority<Period>> getListPeriodeOfTrainingExclusion()
    {
        return listPeriodeOfTrainingExclusion;
    }

    public void setListPeriodeOfTrainingExclusion(List<ConstraintPriority<Period>> listPeriodeOfTrainingExclusion)
    {
        this.listPeriodeOfTrainingExclusion = listPeriodeOfTrainingExclusion;
    }

    public List<ConstraintPriority<Period>> getListPeriodeOfTrainingInclusion()
    {
        return listPeriodeOfTrainingInclusion;
    }

    public void setListPeriodeOfTrainingInclusion(List<ConstraintPriority<Period>> listPeriodeOfTrainingInclusion)
    {
        this.listPeriodeOfTrainingInclusion = listPeriodeOfTrainingInclusion;
    }

    public ConstraintPriority<Boolean> getPrerequisModule()
    {
        return constraintPrerequisite;
    }

    public ConstraintPriority<StudentCompany> getMaxStudentInTraining()
    {
        return maxStudentInTraining;
    }

    public void setMaxStudentInTraining(ConstraintPriority<StudentCompany> maxStudentInTraining)
    {
        this.maxStudentInTraining = maxStudentInTraining;
    }
}

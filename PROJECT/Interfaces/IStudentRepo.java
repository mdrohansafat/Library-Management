package Interfaces;

import Entities.Student;

public interface IStudentRepo 
{
    Student searchStudentByEmail(String email);
    Student[] getAllStudents();
    boolean isEmailRegistered(String email);
    void saveStudentData(Student student);
}

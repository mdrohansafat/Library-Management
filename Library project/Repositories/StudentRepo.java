package Repositories;

import Entities.Student;
import Interfaces.IStudentRepo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepo implements IStudentRepo 
{
    private final String filePath = "Repositories/Data/User.txt"; 

    public StudentRepo() 
    {
        File studentFile = new File(filePath);
        if (!studentFile.exists()) 
        {
            try 
            {
                studentFile.getParentFile().mkdirs();
                studentFile.createNewFile();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public Student searchStudentByEmail(String email) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                Student student = Student.formStudent(line);
                if (student != null && student.getEmail().equals(email)) 
                {
                    return student;
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    public Student[] getAllStudents() 
    {
        List<Student> studentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                Student student = Student.formStudent(line);
                if (student != null) 
                {
                    studentList.add(student);
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return studentList.toArray(new Student[0]); 
    }

    public boolean isEmailRegistered(String email) 
    {
        return searchStudentByEmail(email) != null;
    }

    public void saveStudentData(Student student) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) 
        {
            writer.write(student.toStringStudent());
            writer.newLine();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}

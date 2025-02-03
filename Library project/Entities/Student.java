package Entities;

public class Student 
{
    private String username;
    private String email; 
    private String password;
    private String gender;

    public Student(String username, String email, String password, String gender) 
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
	}

    
    public void setUserName(String username) 
	{
		this.username = username;
		
	}
    public String getUserName()
	{ 
	    return username;
	}

    public void setEmail(String email) 
	{ 
	    this.email = email; 
	}
    public String getEmail() 
	{ 
	    return email; 
	}

    public void setPassword(String password) 
	{
		this.password = password; 
	}
    public String getPassword() 
	{ 
	    return password; 
	}

    public void setGender(String gender) 
	{ 
	    this.gender = gender; 
	}
    public String getGender() 
	{ 
	    return gender; 
	}

   
    public String toStringStudent() 
    {
        return username + "," + email + "," + password + "," + gender;
    }

    public static Student formStudent(String data) 
    {
        String[] fields = data.split(",");
        if (fields.length != 4) 
        {
            System.err.println("Invalid student data: " + data);
            return null;
        }
        return new Student(fields[0], fields[1], fields[2], fields[3]);
    }
}

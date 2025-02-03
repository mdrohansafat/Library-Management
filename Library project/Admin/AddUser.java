package Admin;
import Entities.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddUser
{
    private JFrame frame;
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox;
    private ManageUsers manageUsers;

    public AddUser(ManageUsers manageUsers) 
	{
        this.manageUsers = manageUsers;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Add User");
        frame.setSize(600, 600);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 100, 30);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 200, 30);
        frame.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        frame.add(passwordField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 200, 100, 30);
        frame.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(150, 200, 200, 30);
        frame.add(genderBox);

        JButton registerButton = new JButton("Add");
        registerButton.setBounds(200, 250, 100, 30);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        frame.add(registerButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(310, 250, 100, 30);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        frame.add(clearButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 300, 100, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(backButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void register() 
	{
        String username = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String gender = (String) genderBox.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        if (isEmailExists(email)) 
		{
            JOptionPane.showMessageDialog(frame, "User with this email already exists!");
            return;
        }

        Student student = new Student(username, email, password, gender);

        try 
		{
            FileWriter writer = new FileWriter("Repositories/Data/User.txt", true);
            writer.write(student.getUserName() + "," + student.getEmail() + "," + student.getPassword() + "," + student.getGender() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(frame, "User added successfully!");
            manageUsers.displayUsers();
            frame.dispose();
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    private boolean isEmailExists(String email) 
	{
        try {
            File file = new File("Repositories/Data/User.txt");
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts[1].equals(email)) 
				{
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } 
		catch (IOException ex)
		{
            ex.printStackTrace();
        }
        return false;
    }

    private void clearFields() 
	{
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        genderBox.setSelectedIndex(0);
    }
}


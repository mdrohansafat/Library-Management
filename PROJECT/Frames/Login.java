package Frames;

import Entities.Student;
import Repositories.StudentRepo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login 
{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, adminLoginButton, registerButton;

    public Login() 
	{
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() 
		{
            private final Image backgroundImage = new ImageIcon("Pictures/LOGIN.jpg").getImage();

            protected void paintComponent(Graphics g) 
			{
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("User Email:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        backgroundPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setForeground(Color.WHITE);
        usernameField.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        backgroundPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        backgroundPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setForeground(Color.WHITE);
        passwordField.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        backgroundPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        backgroundPanel.add(loginButton, gbc);

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setFont(new Font("Arial", Font.BOLD, 14));
        adminLoginButton.setForeground(Color.WHITE);
        adminLoginButton.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        backgroundPanel.add(adminLoginButton, gbc);

        JLabel registerLabel = new JLabel("Don't have an account?");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        backgroundPanel.add(registerLabel, gbc);

        registerButton = new JButton("SIGNUP");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        backgroundPanel.add(registerButton, gbc);

        frame.setVisible(true);

        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> 
		{
            frame.dispose();
            new Registration();
        });

        adminLoginButton.addActionListener(e -> 
		{
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isAdmin(email, password)) 
			{
                JOptionPane.showMessageDialog(frame, "Welcome, Admin!");
                frame.dispose();
                new AdminDashboard(); 
            } 
			else 
			{
                JOptionPane.showMessageDialog(frame, "Invalid Admin details");
            }
        });
    }

    private void login() 
	{
        String email = usernameField.getText();
        String password = new String(passwordField.getPassword());
        StudentRepo studentRepo = new StudentRepo();
        Student student = studentRepo.searchStudentByEmail(email);

        if (student != null && student.getPassword().equals(password)) 
		{
            JOptionPane.showMessageDialog(frame, "Login Successful!");
            frame.dispose();
            new UserDashboard(student);  
        } 
		else 
		{
            JOptionPane.showMessageDialog(frame, "Invalid email or password!");
        }
    }
	private boolean isAdmin(String email, String password) 
	{
        String[][] admins = 
		{
                {"admin1@gmail.com", "admin123"},
                {"admin2@gmail.com", "admin456"}, 
                {"admin3@gmail.com", "admin100"}, 
                {"admin4@gmail.com", "admin420"} 
        };

        for (String[] admin : admins)
		{
            if (admin[0].equals(email) && admin[1].equals(password)) 
			{
                return true;
            }
        }
        return false;
    }
}

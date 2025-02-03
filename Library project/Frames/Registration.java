package Frames;
import Entities.Student;
import Repositories.StudentRepo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class Registration 
{
    private JFrame frame;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JLabel passwordStrengthLabel;
    private JComboBox<String> genderComboBox;
    private JButton registerButton, clearButton, backButton;
    private StudentRepo studentRepo; 

    public Registration() 
    {
        studentRepo = new StudentRepo(); 
        frame = new JFrame("User Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() 
        {
            private final Image backgroundImage = new ImageIcon("Pictures/Register.jpg").getImage();

            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to User Registration page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setForeground(Color.WHITE);
        usernameField.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setForeground(Color.WHITE);
        emailField.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.addKeyListener(new KeyAdapter() 
        {
            public void keyReleased(KeyEvent e) 
            {
                updatePasswordStrength();
            }
        });
        passwordField.setForeground(Color.WHITE);
        passwordField.setBackground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        passwordStrengthLabel = new JLabel("Password Strength: ");
        passwordStrengthLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordStrengthLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(passwordStrengthLabel, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        genderLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(genderLabel, gbc);

        genderComboBox = new JComboBox<>(new String[]{"Select", "Male", "Female", "Other"});
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(genderComboBox, gbc);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.addActionListener(e -> register());
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(registerButton, gbc);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(e -> clearFields());
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(clearButton, gbc);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> 
        {
            frame.dispose();
            new Login();
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(backButton, gbc);

        frame.setVisible(true);
    }

    private void updatePasswordStrength() 
    {
        String password = new String(passwordField.getPassword());
        if (password.length() < 6) 
        {
            passwordStrengthLabel.setText("Password Strength: Weak");
            passwordStrengthLabel.setForeground(Color.RED);
        } 
        else if (password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[@#$%^&+=!].*")) 
        {
            passwordStrengthLabel.setText("Password Strength: Strong");
            passwordStrengthLabel.setForeground(Color.GREEN);
        } 
        else 
        {
            passwordStrengthLabel.setText("Password Strength: Medium");
            passwordStrengthLabel.setForeground(Color.ORANGE);
        }
    }

    private void register() 
    {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String gender = (String) genderComboBox.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || gender == null || gender.equals("Select")) 
        {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) 
        {
            JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (studentRepo.isEmailRegistered(email)) 
        {
            JOptionPane.showMessageDialog(frame, "This email is already registered!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(username, email, password, gender);
        studentRepo.saveStudentData(student);

        JOptionPane.showMessageDialog(frame, "Registration successful!");
        clearFields();
    }

    private void clearFields() 
    {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        genderComboBox.setSelectedIndex(0);
        passwordStrengthLabel.setText("Password Strength: ");
        passwordStrengthLabel.setForeground(Color.BLACK);
    }

    private boolean isValidEmail(String email) 
    {
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }
}

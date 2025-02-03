package Admin;
import Frames.AdminDashboard;
import Frames.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Color;
import java.awt.Font;


public class ManageUsers implements ActionListener {
    private JFrame frame;

    public ManageUsers() {
        frame = new JFrame("Library Management System");
        frame.setSize(600, 600);
        frame.setLayout(null);

        
        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setBounds(220, 10, 250, 40); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        frame.add(titleLabel);
       
        ImageIcon backgroundImage = new ImageIcon("Pictures/background3.jpg"); 
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(backgroundLabel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); 
        buttonPanel.setBounds(150, 80, 300, 250); 
        buttonPanel.setOpaque(false);

     
        JButton displayUsersButton = new JButton("Display Users");
        displayUsersButton.addActionListener(this);
        buttonPanel.add(displayUsersButton);

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(this);
        buttonPanel.add(addUserButton);

        JButton updateUserButton = new JButton("Update User");
        updateUserButton.addActionListener(this);
        buttonPanel.add(updateUserButton);

        JButton searchUserButton = new JButton("Search User");
        searchUserButton.addActionListener(this);
        buttonPanel.add(searchUserButton);

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.addActionListener(this);
        buttonPanel.add(deleteUserButton);

        frame.add(buttonPanel);

     
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 20, 100, 30); 
        backButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
			{
                frame.dispose();
                new AdminDashboard();
            }
        });
        frame.add(backButton);

        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(500, 20, 80, 30); 
        logoutButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Login();
            }
        });
        frame.add(logoutButton);

       
        frame.getContentPane().setComponentZOrder(backgroundLabel, frame.getContentPane().getComponentCount() - 1);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() instanceof JButton) 
		{
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Display Users")) 
			{
                displayUsers();
            } 
			else if (button.getText().equals("Add User")) 
			{
                frame.dispose();
				new AddUser(new ManageUsers());
            } 
			else if (button.getText().equals("Update User")) 
			{
                updateUserInfo();
            } 
			else if (button.getText().equals("Search User")) 
			{
                searchUser();
            } 
			else if (button.getText().equals("Delete User")) 
			{
                deleteUser();
            }
        }
    }

    public void displayUsers() 
	{
        try {
            File file = new File("Repositories/Data/User.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No users registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder usersInfo = new StringBuilder();
            int numUsers = countLines(file);
            String[] usersData = new String[numUsers];
            int index = 0;
            while ((line = reader.readLine()) != null) 
			{
                usersData[index++] = line;
                String[] parts = line.split(",");
                String userInfo = String.format("Name: %s, Email: %s, Gender: %s\n", parts[0], parts[1], parts[3]);
                usersInfo.append(userInfo);
            }
            reader.close();

            JTextArea textArea = new JTextArea(usersInfo.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Registered Users", JOptionPane.PLAIN_MESSAGE);
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    private void updateUserInfo() 
	{
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to update:");
        if (email == null || email.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!");
            return;
        }
		frame.dispose();
		new UpdateUser(email, new ManageUsers());
        
    }

    private void searchUser() 
	{
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to search:");
        if (email == null || email.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!");
            return;
        }

        try {
            File file = new File("Repositories/Data/User.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No users registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder userInfo = new StringBuilder();
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts[1].equals(email)) {
                    userInfo.append(String.format("Name: %s, Email: %s, Gender: %s\n", parts[0], parts[1], parts[3]));
                    break;
                }
            }
            reader.close();

            if (userInfo.length() > 0) 
			{
                JOptionPane.showMessageDialog(frame, userInfo.toString(), "User Details", JOptionPane.PLAIN_MESSAGE);
            } 
			else 
			{
                JOptionPane.showMessageDialog(frame, "User not found!");
            }
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    private void deleteUser() 
	{
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to delete:");
        if (email == null || email.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!");
            return;
        }

        String[][] lines = null;
        try {
            File file = new File("Repositories/Data/User.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No users registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int numUsers = countLines(file);
            lines = new String[numUsers][];
            int count = 0;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (!parts[1].equals(email)) 
				{
                    lines[count++] = parts;
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count; i++) 
			{
                writer.write(String.join(",", lines[i]) + "\n");
            }
            writer.close();

            JOptionPane.showMessageDialog(frame, "User deleted successfully!");
        }
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    private int countLines(File file) throws IOException 
	{
        int lines = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
}

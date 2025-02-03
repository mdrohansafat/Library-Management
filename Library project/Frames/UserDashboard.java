package Frames;
import user.*;
import Entities.Student;
import Entities.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UserDashboard implements ActionListener 
{
    private JFrame frame;
    private Student student;
    private ShoppingCart cart;

    public UserDashboard(Student student) 
	{
        this.student=student ;
        this.cart = new ShoppingCart();

        initializeUI();
    }

    
   private void initializeUI() 
   {
        frame = new JFrame("User Dashboard");
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel welcomeLabel = createWelcomeLabel();
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.CENTER);

        JButton logoutButton = createLogoutButton();
        frame.add(logoutButton, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);  
        }

    private JLabel createWelcomeLabel() 
	{
        JLabel welcomeLabel = new JLabel("Welcome to City Library, " + student.getUserName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 102, 204)); 
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 
        return welcomeLabel;
    }

    private JPanel createButtonPanel() 
	{
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20)); 
        buttonPanel.setBackground(new Color(240, 248, 255)); 

        buttonPanel.add(createButtonWithImage("Pictures/updateProfile.jpg", "Update Profile"));
        buttonPanel.add(createButtonWithImage("Pictures/viewProfile.jpg", "View Profile"));
        buttonPanel.add(createButtonWithImage("Pictures/viewAvailableBooks.jpg", "View Available Books"));

        return buttonPanel;
    }
	

    private JButton createLogoutButton() 
	{
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setBackground(new Color(255, 69, 0)); 
        logoutButton.setForeground(Color.WHITE); 
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> 
		{
            frame.dispose();
            new Login();
        });
        return logoutButton;
    }

    private JPanel createButtonWithImage(String imagePath, String buttonText) 
	{
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 2)); 
        try 
		{
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); 
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);
        } 
		catch (Exception e) 
		{
            JLabel errorLabel = new JLabel("Image not found");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180)); 
        button.setForeground(Color.WHITE); 
        button.setFocusPainted(false); 
        button.setActionCommand(buttonText);
        button.addActionListener(this);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    
    public void actionPerformed(ActionEvent e) 
	{
        switch (e.getActionCommand()) 
		{
            case "View Profile" -> viewProfile();
            case "Update Profile" -> updateProfile();
            case "View Available Books" -> viewAvailableBooks();
        }
    }

    
   private void viewProfile() 
   {
    
    JDialog profileDialog = new JDialog(frame, "User Profile", true);
    profileDialog.setSize(300, 250);
    profileDialog.setLocationRelativeTo(frame);
    profileDialog.setLayout(new BorderLayout());

    
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(0, 2, 10, 10));
    infoPanel.setBackground(new Color(240, 248, 255));  

  
    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    nameLabel.setForeground(new Color(70, 130, 180));  
    JLabel nameValue = new JLabel(student.getUserName());
    nameValue.setFont(new Font("Arial", Font.PLAIN, 14));

    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
    emailLabel.setForeground(new Color(70, 130, 180));
    JLabel emailValue = new JLabel(student.getEmail());
    emailValue.setFont(new Font("Arial", Font.PLAIN, 14));

   
    JLabel genderLabel = new JLabel("Gender:");
    genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
    genderLabel.setForeground(new Color(70, 130, 180));
    JLabel genderValue = new JLabel(student.getGender());
    genderValue.setFont(new Font("Arial", Font.PLAIN, 14));

    
    infoPanel.add(nameLabel);
    infoPanel.add(nameValue);
    infoPanel.add(emailLabel);
    infoPanel.add(emailValue);
    infoPanel.add(genderLabel);
    infoPanel.add(genderValue);

    
    JPanel buttonPanel = new JPanel();
    JButton closeButton = new JButton("Close");
    closeButton.setFont(new Font("Arial", Font.BOLD, 14));
    closeButton.setBackground(new Color(70, 130, 180)); 
    closeButton.setForeground(Color.WHITE);
    closeButton.setFocusPainted(false);
    closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    closeButton.addActionListener(new ActionListener() 
	{
        public void actionPerformed(ActionEvent e) 
		{
            profileDialog.dispose();
        }
    });
    buttonPanel.add(closeButton);

   
    profileDialog.add(infoPanel, BorderLayout.CENTER);
    profileDialog.add(buttonPanel, BorderLayout.SOUTH);

   
    profileDialog.setVisible(true);
}


    
    private void updateProfile() 
	{
        JFrame updateFrame = new JFrame("Update Profile");
        updateFrame.setSize(450, 250); 
        updateFrame.setLayout(new BorderLayout());

        JPanel fieldPanel = createProfileFieldPanel();
        updateFrame.add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createUpdateButtonPanel(updateFrame);
        updateFrame.add(buttonPanel, BorderLayout.SOUTH);

        updateFrame.setVisible(true);
        updateFrame.setLocationRelativeTo(null);
    }

    
    private JPanel createProfileFieldPanel() 
	{
        JPanel fieldPanel = new JPanel(new GridLayout(4, 2, 10, 10)); 
        fieldPanel.setBackground(new Color(255, 255, 255)); 

        JTextField nameField = new JTextField(student.getUserName());
        JTextField emailField = new JTextField(student.getEmail());
        JTextField passwordField = new JTextField(student.getPassword());
        JTextField genderField = new JTextField(student.getGender());
		 emailField.setEditable(false);

        fieldPanel.add(new JLabel("Name:"));
        fieldPanel.add(nameField);
        fieldPanel.add(new JLabel("Email:"));
        fieldPanel.add(emailField);
        fieldPanel.add(new JLabel("Password:"));
        fieldPanel.add(passwordField);
        fieldPanel.add(new JLabel("Gender:"));
        fieldPanel.add(genderField);

        return fieldPanel;
    }

    
    private JPanel createUpdateButtonPanel(JFrame updateFrame) 
	{
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updateButton.setBackground(new Color(34, 139, 34)); 
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> {
            updateUserProfile(updateFrame);
        });
        buttonPanel.add(updateButton);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setBackground(new Color(255, 69, 0)); 
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> updateFrame.dispose());
        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    
    private void updateUserProfile(JFrame updateFrame) 
	{
        JTextField nameField = (JTextField) ((JPanel) updateFrame.getContentPane().getComponent(0)).getComponent(1);
        JTextField emailField = (JTextField) ((JPanel) updateFrame.getContentPane().getComponent(0)).getComponent(3);
        JTextField passwordField = (JTextField) ((JPanel) updateFrame.getContentPane().getComponent(0)).getComponent(5);
        JTextField genderField = (JTextField) ((JPanel) updateFrame.getContentPane().getComponent(0)).getComponent(7);

        student.setUserName(nameField.getText());
        student.setEmail(emailField.getText());
        student.setPassword(passwordField.getText());
        student.setGender(genderField.getText());

        updateUserDataFile(student);

        JOptionPane.showMessageDialog(updateFrame, "Profile updated successfully!");
    }

    
  private void updateUserDataFile(Student updatedStudent) 
  {
    File file = new File("Repositories/Data/User.txt");
    if (!file.exists()) 
	{
        JOptionPane.showMessageDialog(frame, "User data file not found!");
        return;
    }

    try 
	{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String oldEmail = student.getEmail(); 
        String line;

        while ((line = reader.readLine()) != null) 
		{
            String[] parts = line.split(",");
            if (parts.length == 4 && parts[1].equals(oldEmail)) 
			{
                
                line = updatedStudent.toStringStudent();
            }
            sb.append(line).append("\n");
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(sb.toString());
        writer.close();

        
        this.student = updatedStudent;
    }
	catch (IOException ex) 
	{
        ex.printStackTrace();
    }
}

   
   private void viewAvailableBooks() 
   {
    JFrame booksFrame = new JFrame("Available Books");
    booksFrame.setSize(700, 500);
    booksFrame.setLayout(new BorderLayout());

    
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JTextField searchField = new JTextField(20);
    JButton searchButton = new JButton("Search");

    searchPanel.add(new JLabel("Search:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    JPanel booksPanel = new JPanel();
    booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
    booksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(booksPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

  
    loadBooks(booksPanel, "");

   
    searchButton.addActionListener(e -> 
	{
        String query = searchField.getText().trim().toLowerCase();
        booksPanel.removeAll();
        loadBooks(booksPanel, query);
        booksPanel.revalidate();
        booksPanel.repaint();
    });

    booksFrame.add(searchPanel, BorderLayout.NORTH);
    booksFrame.add(scrollPane, BorderLayout.CENTER);

    booksFrame.setVisible(true);
    booksFrame.setLocationRelativeTo(frame);
}


 private void loadBooks(JPanel booksPanel, String query) {
    try {
        File file = new File("Repositories/Data/BookLIst.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "No books available!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String title = parts[0];
            String ISBN = parts[1];
            String author = parts[2];
            double price = Double.parseDouble(parts[3]);
            int quantity = Integer.parseInt(parts[4]);

            if (query.isEmpty() || title.toLowerCase().contains(query) || author.toLowerCase().contains(query)) {
                found = true;
                Book book = new Book(title, ISBN, author, price, quantity);
                JPanel bookItemPanel = new JPanel(new BorderLayout(10, 10));
                bookItemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
                bookItemPanel.setBackground(new Color(245, 245, 245));
                bookItemPanel.setPreferredSize(new Dimension(550, 100));

                JLabel bookLabel = new JLabel("<html><b>" + title + "</b><br>Author: " + author + "<br>Price: $" + price + "<br>Available Quantity: " + quantity + "</html>");
                bookLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                bookItemPanel.add(bookLabel, BorderLayout.CENTER);

                JButton detailsButton = new JButton("Buy");
                detailsButton.setBackground(new Color(70, 130, 180));
                detailsButton.setForeground(Color.WHITE);
                detailsButton.setFocusPainted(false);
                detailsButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                
                detailsButton.addActionListener(e -> {
                    String input = JOptionPane.showInputDialog(frame, "Enter quantity to add to cart:");
                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            int userQuantity = Integer.parseInt(input);

                            if (userQuantity <= 0) {
                                JOptionPane.showMessageDialog(frame, "Please enter a positive quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (userQuantity > book.getAvailableQuantity()) {
                                JOptionPane.showMessageDialog(frame, "Requested quantity exceeds available stock.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                cart.addItem(book, userQuantity);
                                book.setAvailableQuantity(book.getAvailableQuantity() - userQuantity); // Reduce stock
                                JOptionPane.showMessageDialog(frame, userQuantity + " copies of " + title + " added to cart!");
                            }

                            int option = JOptionPane.showConfirmDialog(frame, "Do you want to select more books?", "Select More Books", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.NO_OPTION) {
                                if (cart.getItems().isEmpty()) {
                                    JOptionPane.showMessageDialog(frame, "Your cart is empty. Please add books to your cart before proceeding to payment.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    new PaymentFrame(cart);
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Quantity cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                });

                bookItemPanel.add(detailsButton, BorderLayout.EAST);
                booksPanel.add(Box.createVerticalStrut(10));
                booksPanel.add(bookItemPanel);
            }
        }
        reader.close();

        if (!found) 
		{
            booksPanel.add(new JLabel("No books found."));
            booksPanel.revalidate();
            booksPanel.repaint();
        }
    } 
	catch (IOException ex) 
	{
        ex.printStackTrace();
        JOptionPane.showMessageDialog(frame, "An error occurred while loading books.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}

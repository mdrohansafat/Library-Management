package Admin;
import Interfaces.IManageBooksOperations;
import Frames.AdminDashboard;
import Frames.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Color;
import java.awt.Font;


public class ManageBooks implements ActionListener, IManageBooksOperations {
    private JFrame frame;

    public ManageBooks() 
	{
        frame = new JFrame("Library Management System");
        frame.setSize(600, 600);
        frame.setLayout(null);

       
        JLabel titleLabel = new JLabel("Manage Books");
        titleLabel.setBounds(220, 10, 250, 40); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20)); 
        frame.add(titleLabel);

       
        ImageIcon backgroundImage = new ImageIcon("Pictures/background2.jpg"); 
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(backgroundLabel);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); 
        buttonPanel.setBounds(150, 80, 300, 250); 
        buttonPanel.setOpaque(false); 

        
        JButton displayBooksButton = new JButton("Display Books");
        displayBooksButton.addActionListener(this);
        buttonPanel.add(displayBooksButton);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(this);
        buttonPanel.add(addBookButton);

        JButton updateBookButton = new JButton("Update Book");
        updateBookButton.addActionListener(this);
        buttonPanel.add(updateBookButton);

        JButton searchBookButton = new JButton("Search Book");
        searchBookButton.addActionListener(this);
        buttonPanel.add(searchBookButton);

        JButton deleteBookButton = new JButton("Delete Book");
        deleteBookButton.addActionListener(this);
        buttonPanel.add(deleteBookButton);

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
            public void actionPerformed(ActionEvent e) 
			{
                frame.dispose();
                new Login();
            }
        });
        frame.add(logoutButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        
        frame.getContentPane().setComponentZOrder(backgroundLabel, frame.getContentPane().getComponentCount() - 1);
    }


    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() instanceof JButton) 
		{
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Display Books")) 
			{
                displayBooks();
            }
			else if (button.getText().equals("Add Book")) 
			{
                new AddBook();
            }
			else if (button.getText().equals("Update Book")) 
			{
                updateBook();
            } 
			else if (button.getText().equals("Search Book")) 
			{
                searchBook();
            } 
			else if (button.getText().equals("Delete Book")) 
			{
                deleteBook();
            }
        }
    }

    public void displayBooks() 
	{
        try {
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No books available!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder booksInfo = new StringBuilder();
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                String bookInfo = String.format("Title: %s, ISBN: %s, Author: %s, Price: $%.2f, Quantity: %s\n", parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
                booksInfo.append(bookInfo);
            }
            reader.close();

            JTextArea textArea = new JTextArea(booksInfo.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Available Books", JOptionPane.PLAIN_MESSAGE);
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    public void updateBook() 
	{
        String ISBN = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to update:");
        if (ISBN == null || ISBN.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "ISBN cannot be empty!");
            return;
        }
		frame.dispose();
		new UpdateBook(ISBN, new ManageBooks());
      
    }

    public void searchBook() 
	{
        String ISBN = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to search:");
        if (ISBN == null || ISBN.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "ISBN cannot be empty!");
            return;
        }

        try 
		{
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No books available!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder bookInfo = new StringBuilder();
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts[1].equals(ISBN)) 
				{
                    bookInfo.append(String.format("Title: %s, ISBN: %s, Author: %s, Price: $%.2f, Quantity: %s\n", parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]));
                    break;
                }
            }
            reader.close();

            if (bookInfo.length() > 0) 
			{
                JOptionPane.showMessageDialog(frame, bookInfo.toString(), "Book Details", JOptionPane.PLAIN_MESSAGE);
            } 
			else 
			{
                JOptionPane.showMessageDialog(frame, "Book not found!");
            }
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    public void deleteBook() 
	{
        String ISBN = JOptionPane.showInputDialog(frame, "Enter ISBN of the book to delete:");
        if (ISBN == null || ISBN.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "ISBN cannot be empty!");
            return;
        }

        String[] lines = new String[100]; 
        int count = 0;

        try 
		{
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No books available!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (!parts[1].equals(ISBN)) 
				{
                    lines[count++] = line;
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < count; i++) 
			{
                writer.write(lines[i] + "\n");
            }
            writer.close();

            JOptionPane.showMessageDialog(frame, "Book deleted successfully!");
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }
}


package Admin;
import Frames.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UpdateBook 
{
    private JFrame frame;
    private JTextField titleField, isbnField, authorField, unitPriceField, quantityField;
    private String isbn;
    private ManageBooks manageBooks;

    public UpdateBook(String isbn, ManageBooks manageBooks) 
	{
        this.isbn = isbn;
        this.manageBooks = manageBooks;
        initialize();
    }

    private void initialize() 
	{
        frame = new JFrame("Update Book");
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(titleField, gbc);

        JLabel isbnLabel = new JLabel("ISBN:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(isbnLabel, gbc);

        isbnField = new JTextField(20);
        isbnField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(isbnField, gbc);

        JLabel authorLabel = new JLabel("Author:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(authorLabel, gbc);

        authorField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(authorField, gbc);

        JLabel unitPriceLabel = new JLabel("Unit Price:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(unitPriceLabel, gbc);

        unitPriceField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(unitPriceField, gbc);

        JLabel quantityLabel = new JLabel("Quantity:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(quantityLabel, gbc);

        quantityField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(quantityField, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e)
			{
                updateBook();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(updateButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e)
			{
                frame.dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(backButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
			{
                frame.dispose();
                new Login();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(logoutButton, gbc);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        loadBookData();
    }

    private void loadBookData() 
	{
        try {
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No books registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts[1].equals(isbn)) {
                    titleField.setText(parts[0]);
                    isbnField.setText(parts[1]);
                    authorField.setText(parts[2]);
                    unitPriceField.setText(parts[3]);
                    quantityField.setText(parts[4]);
                    break;
                }
            }
            reader.close();
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }

    private void updateBook() 
	{
        String title = titleField.getText();
        String isbn = isbnField.getText();
        String author = authorField.getText();
        double unitPrice = Double.parseDouble(unitPriceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        if (title.isEmpty() || author.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Please fill all required fields!");
            return;
        }

        try 
		{
            File file = new File("books.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(frame, "No books registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts[1].equals(isbn)) 
				{
                    updatedData.append(String.join(",", title, isbn, author, String.valueOf(unitPrice), String.valueOf(quantity))).append("\n");
                } else {
                    updatedData.append(line).append("\n");
                }
            }
            reader.close();

            FileWriter writer = new FileWriter(file);
            writer.write(updatedData.toString());
            writer.close();

            JOptionPane.showMessageDialog(frame, "Book information updated successfully!");
            frame.dispose();
            manageBooks.displayBooks(); 
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
    }
}


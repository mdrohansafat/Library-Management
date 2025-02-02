package Admin;
import Entities.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddBook implements ActionListener 
{
    private JFrame frame;
    private JTextField titleField, isbnField, authorField, priceField, quantityField;

    public AddBook() {
        frame = new JFrame("Add Book");
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Add Book");
        titleLabel.setBounds(250, 20, 100, 30);
        frame.add(titleLabel);

        JLabel titleLabel2 = new JLabel("Title:");
        titleLabel2.setBounds(50, 60, 100, 30);
        frame.add(titleLabel2);

        titleField = new JTextField();
        titleField.setBounds(150, 60, 200, 30);
        frame.add(titleField);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(50, 100, 100, 30);
        frame.add(isbnLabel);

        isbnField = new JTextField();
        isbnField.setBounds(150, 100, 200, 30);
        frame.add(isbnField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(50, 140, 100, 30);
        frame.add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(150, 140, 200, 30);
        frame.add(authorField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 180, 100, 30);
        frame.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(150, 180, 200, 30);
        frame.add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 220, 100, 30);
        frame.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(150, 220, 200, 30);
        frame.add(quantityField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(250, 280, 100, 30);
        addButton.addActionListener(this);
        frame.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(360, 280, 100, 30);
        clearButton.addActionListener(this);
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() instanceof JButton) 
		{
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Add")) 
			{
                addBook();
            } else if (button.getText().equals("Clear")) 
			{
                clearFields();
            }
        }
    }

    private void addBook() {
        String title = titleField.getText();
        String isbn = isbnField.getText();
        String author = authorField.getText();
        String priceStr = priceField.getText();
        String quantityStr = quantityField.getText();

        if (title.isEmpty() || isbn.isEmpty() || author.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) 
		{
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            
            if (isISBNExists(isbn)) {
                JOptionPane.showMessageDialog(frame, "Book with the same ISBN already exists!");
                clearFields(); 
                return;
            }

            Book newBook = new Book(title, isbn, author, price, quantity);

            FileWriter writer = new FileWriter("Repositories/Data/BookLIst.txt", true);
            writer.write(newBook.getTitle() + "," + newBook.getISBN() + "," + newBook.getAuthor() + "," + newBook.getUnitPrice() + "," + newBook.getAvailableQuantity() + "\n");
            writer.close();

            JOptionPane.showMessageDialog(frame, "Book added successfully!");
            frame.dispose(); 
        } 
		catch (NumberFormatException | IOException ex) 
		{
            JOptionPane.showMessageDialog(frame, "Invalid price or quantity format!");
        }
    }

    private void clearFields() 
	{
        titleField.setText("");
        isbnField.setText("");
        authorField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }

    private boolean isISBNExists(String isbn) {
        try {
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(isbn)) 
				{
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}


package Frames;
import user.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PaymentFrame extends JFrame 
{
    private ShoppingCart cart;

    public PaymentFrame(ShoppingCart cart) 
	{
        this.cart = cart;

        
        setTitle("Payment");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel backgroundPanel = new JPanel() 
		{
            protected void paintComponent(Graphics g) 
			{
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("Pictures/card.jpg"); 
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        JLabel totalLabel = new JLabel("Total Price: $" + cart.getTotalPrice());
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 22));
        totalLabel.setForeground(new Color(255, 255, 255)); 
        totalLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        backgroundPanel.add(totalLabel, BorderLayout.NORTH);

       
        JPanel paymentPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        paymentPanel.setOpaque(false);
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cardNumberLabel.setForeground(Color.WHITE);
        JTextField cardNumberField = new JTextField();
        paymentPanel.add(cardNumberLabel);
        paymentPanel.add(cardNumberField);

        JLabel expiryDateLabel = new JLabel("Expiry Date (MM/YYYY):");
        expiryDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        expiryDateLabel.setForeground(Color.WHITE);
        JTextField expiryDateField = new JTextField();
        paymentPanel.add(expiryDateLabel);
        paymentPanel.add(expiryDateField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        cvvLabel.setForeground(Color.WHITE);
        JTextField cvvField = new JTextField();
        paymentPanel.add(cvvLabel);
        paymentPanel.add(cvvField);

       
        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paymentMethodLabel.setForeground(Color.WHITE);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false); 

        JRadioButton visaButton = new JRadioButton("Visa");
        JRadioButton mastercardButton = new JRadioButton("MasterCard");
        JRadioButton paypalButton = new JRadioButton("PayPal");
        JRadioButton otherButton = new JRadioButton("Other");

        
        visaButton.setOpaque(false);
        visaButton.setForeground(Color.WHITE);
        mastercardButton.setOpaque(false);
        mastercardButton.setForeground(Color.WHITE);
        paypalButton.setOpaque(false);
        paypalButton.setForeground(Color.WHITE);
        otherButton.setOpaque(false);
        otherButton.setForeground(Color.WHITE);

        
        ButtonGroup paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(visaButton);
        paymentMethodGroup.add(mastercardButton);
        paymentMethodGroup.add(paypalButton);
        paymentMethodGroup.add(otherButton);

        radioPanel.add(visaButton);
        radioPanel.add(mastercardButton);
        radioPanel.add(paypalButton);
        radioPanel.add(otherButton);

        paymentPanel.add(paymentMethodLabel);
        paymentPanel.add(radioPanel);

        backgroundPanel.add(paymentPanel, BorderLayout.CENTER);

     
        JButton payButton = new JButton("Pay Now");
        payButton.setBackground(new Color(70, 130, 180)); 
        payButton.setForeground(Color.WHITE);
        payButton.setFont(new Font("Arial", Font.BOLD, 16));
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        payButton.addActionListener(new ActionListener() 
		{
           
            public void actionPerformed(ActionEvent e) 
			{
                String cardNumber = cardNumberField.getText().trim();
                String expiryDate = expiryDateField.getText().trim();
                String cvv = cvvField.getText().trim();
				
                String paymentMethod = null;
                if (visaButton.isSelected()) 
				{
                    paymentMethod = "Visa";
                } 
				else if (mastercardButton.isSelected()) 
				{
                    paymentMethod = "MasterCard";
                } 
				else if (paypalButton.isSelected()) 
				{
                    paymentMethod = "PayPal";
                }
				else if (otherButton.isSelected()) 
				{
                    paymentMethod = "Other";
                }

               if (!cardNumber.matches("^\\d{16}$")) 
			   {
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Invalid card number! Must be 16 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
 
                if (!expiryDate.matches("^(0[1-9]|1[0-2])/\\d{4}$"))
                {
                  JOptionPane.showMessageDialog(PaymentFrame.this, "Invalid expiry date! Use MM/YYYY format.", "Error", JOptionPane.ERROR_MESSAGE);
                   return;
                }
 
                if (!cvv.matches("^\\d{3}$")) 
				{
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Invalid CVV! Must be 3 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
 
                if (paymentMethod == null) 
				{
                    JOptionPane.showMessageDialog(PaymentFrame.this, "Please select a payment method.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
 
                JOptionPane.showMessageDialog(PaymentFrame.this, "Payment Successful using " + paymentMethod + "!", "Payment", JOptionPane.INFORMATION_MESSAGE);
                updateBookQuantities();
                cart.clear();
                dispose();
                
            }
        });

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonPanel.add(payButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    
    private void updateBookQuantities() 
	{
        try {
            File file = new File("Repositories/Data/BookLIst.txt");
            if (!file.exists()) 
			{
                JOptionPane.showMessageDialog(this, "Books data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) 
			{
                String[] parts = line.split(",");
                String ISBN = parts[1];

                for (CartItem item : cart.getItems()) 
				{
                    if (item.getBook().getISBN().equals(ISBN)) 
					{
                        int newQuantity = Integer.parseInt(parts[4]) - item.getQuantity();
                        parts[4] = String.valueOf(newQuantity);
                    }
                }
                sb.append(String.join(",", parts)).append("\n");
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());
            writer.close();
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating book quantities.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package user;
import Entities.*;
import Frames.PaymentFrame;
import javax.swing.*;

public class Payment 
{
    private double totalAmount;

    public Payment(Book[] items, int[] quantities, int size) 
	{
        calculateTotalAmount(items, quantities, size);
    }

    private void calculateTotalAmount(Book[] items, int[] quantities, int size) 
	{
        totalAmount = 0;
        for (int i = 0; i < size; i++) 
		{
            totalAmount += items[i].getUnitPrice() * quantities[i];
        }
    }

    public double getTotalAmount() 
	{
        return totalAmount;
    }

    public boolean processPayment() 
	{
        
        String cardNumber = JOptionPane.showInputDialog(null, "Enter your card number:");
        String cvv = JOptionPane.showInputDialog(null, "Enter your CVV:");

        if (cardNumber == null || cvv == null || cardNumber.trim().isEmpty() || cvv.trim().isEmpty()) 
		{
            JOptionPane.showMessageDialog(null, "Card number and CVV cannot be empty.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) 
		{
            JOptionPane.showMessageDialog(null, "Invalid card number! It must be 16 digits.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cvv.length() != 3 || !cvv.matches("\\d+")) 
		{
            JOptionPane.showMessageDialog(null, "Invalid CVV! It must be 3 digits.", "Payment Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        
        JOptionPane.showMessageDialog(null, "Payment successful! Total Amount: $" + totalAmount, "Payment Success", JOptionPane.PLAIN_MESSAGE);
        return true;
    }
}

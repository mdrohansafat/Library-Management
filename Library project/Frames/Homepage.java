package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage 
{
    private JFrame frame;
    private JButton proceed;

    public Homepage() 
	{
        frame = new JFrame("Homepage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        JPanel customPanel = new JPanel() 
		{
            private final Image backgroundImage = new ImageIcon("Pictures/HomePage.jpg").getImage();

       
            protected void paintComponent(Graphics g) 
			{
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        customPanel.setLayout(new GridBagLayout());
        frame.setContentPane(customPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to our Homepage", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        customPanel.add(titleLabel, gbc);

        proceed = new JButton("Ready to Proceed");
        proceed.setFont(new Font("Arial", Font.BOLD, 14));
        proceed.setForeground(Color.WHITE);
        proceed.setBackground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        customPanel.add(proceed, gbc);

        frame.setVisible(true);

        proceed.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
			{
                frame.dispose();
                new Login();
            }
        });
    }
}
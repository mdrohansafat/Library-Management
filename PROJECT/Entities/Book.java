package Entities;

public class Book 
{
    private String title;
    private String ISBN;
    private String author;
    private double unitPrice;
    private int availableQuantity;

    public Book(String title, String ISBN, String author, double unitPrice, int availableQuantity) 
	{
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
    }

    public String getTitle() 
	{
        return title;
    }

    public String getISBN() 
	{
        return ISBN;
    }

    public String getAuthor()
	{
        return author;
    }

    public double getUnitPrice() 
	{
        return unitPrice;
    }

    public int getAvailableQuantity() 
	{
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) 
	{
        this.availableQuantity = availableQuantity;
    }

    public boolean isAvailable(int quantity) 
	{
        return quantity > 0 && quantity <= availableQuantity;
    }


    public String toString() 
	{
        return "Book{" +
                "title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
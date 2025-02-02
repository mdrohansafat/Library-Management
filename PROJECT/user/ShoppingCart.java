package user;
import Entities.Book;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart 
{
    private List<CartItem> items;

    public ShoppingCart() 
	{
        items = new ArrayList<>();
    }

    public void addItem(Book book, int quantity) 
	{
        if (book == null || quantity <= 0) 
		{
            throw new IllegalArgumentException("Book cannot be null and quantity must be greater than 0.");
        }
        
        boolean itemExists = false;
        for (CartItem item : items) 
		{
            if (item.getBook().getISBN().equals(book.getISBN())) 
			{
                item.setQuantity(item.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }
        
        if (!itemExists) 
		{
            items.add(new CartItem(book, quantity));
        }
    }

    public void removeItem(Book book) 
	{
        if (book == null) 
		{
            throw new IllegalArgumentException("Book cannot be null.");
        }
        items.removeIf(item -> item.getBook().getISBN().equals(book.getISBN()));
    }

    public double getTotalPrice() 
	{
        double total = 0;
        for (CartItem item : items) 
		{
            total += item.getBook().getUnitPrice() * item.getQuantity();
        }
        return total;
    }

    public List<CartItem> getItems() 
	{
        return items;
    }

    public void clear() 
	{
        items.clear();
    }
    
    public boolean isEmpty() 
	{
        return items.isEmpty();
    }
}

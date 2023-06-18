package sub1;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return name + " - $" + String.format("%.2f", price);
    }

	public int getStock() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setStock(int i) {
		// TODO Auto-generated method stub
		
	}

	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
}

package sub1;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void clearProductList() {
        productList.clear();
    }

    public String getOrderDetails() {
        StringBuilder sb = new StringBuilder();

        for (Product product : productList) {
            sb.append(product.toString()).append("\n");
        }

        return sb.toString();
    }
}

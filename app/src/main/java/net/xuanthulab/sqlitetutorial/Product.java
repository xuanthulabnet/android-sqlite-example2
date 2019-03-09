package net.xuanthulab.sqlitetutorial;

//Model phần tử dữ liệu hiện
public class Product {
    String name;
    int price;
    int productID;

    public Product(int productID, String name, int price) {
        this.name = name;
        this.price = price;
        this.productID = productID;
    }

}
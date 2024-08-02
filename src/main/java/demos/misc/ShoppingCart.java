package demos.misc;

import java.util.ArrayList;
import java.util.List;

/*
* Objects: demos.misc.ShoppingCart, demos.misc.Product, Coupon
* */
class Demo{
    public static void main(String[] args) {
        Product jeans = new Item1("Jeans", 1500, ProductType.CLOTHING, 1);
        ShoppingCart cart = new ShoppingCart();

        cart.addToCart(jeans);
        System.out.println("Total price: "+ cart.getCartPrice());
    }
}

public class ShoppingCart {
    List<Product> productList;

    public ShoppingCart() {
        this.productList = new ArrayList<>();
    }

    void addToCart(Product product){
        Product productAfterDiscount = new PercentCouponDecorator(
                new TypeCouponDecorator(product, 10), 10);
        productList.add(productAfterDiscount);
    }

    double getCartPrice(){
        double total = 0;

        for (Product product : productList){
            total += product.getPrice();
        }
        return total;
    }
}

enum ProductType{
    ELECTRONICS, FURNITURE, CLOTHING
}

abstract class Product{
    String name;
    double price;
    ProductType productType;
    int quantity;

    Product(String name, double price, ProductType productType, int quantity) {
        this.name = name;
        this.price = price;
        this.productType = productType;
    }

    Product(){}

    public ProductType getProductType() {
        return productType;
    }

    public abstract double getPrice();
}

class Item1 extends Product{

    Item1(String name, double price, ProductType productType, int quantity) {
        super(name, price, productType, quantity);
    }

    @Override
    public double getPrice() {
        return super.price;
    }
}

class Item2 extends Product{
    Item2(String name, double price, ProductType productType, int quantity) {
        super(name, price, productType, quantity);
    }

    @Override
    public double getPrice() {
        return super.price;
    }
}

abstract class CouponDecorator extends Product{ }
class PercentCouponDecorator extends CouponDecorator{
    Product product;
    double discountPercent;

    PercentCouponDecorator(Product product, double discountPercent){
        this.product = product;
        this.discountPercent = discountPercent;
    }

    @Override
    public double getPrice() {
        double price = product.getPrice();
        price = price - (price * discountPercent) / 100;
        System.out.println("price after demos.misc.PercentCouponDecorator: " + price);
        return price;
    }
}

class TypeCouponDecorator extends CouponDecorator{
    Product product;
    double discountPercent;
    List<ProductType> eligibleTypes;

    TypeCouponDecorator(Product product, double discountPercent){
        this.product = product;
        this.discountPercent = discountPercent;
        this.eligibleTypes = new ArrayList<>();
        eligibleTypes.add(ProductType.FURNITURE);
        eligibleTypes.add(ProductType.CLOTHING);
    }

    @Override
    public double getPrice() {
        double price = product.getPrice();
        if(eligibleTypes.contains(product.getProductType())){
            price = price - (price * discountPercent) / 100;
        }
        System.out.println("price after demos.misc.PercentCouponDecorator: " + price);
        return price;
    }
}

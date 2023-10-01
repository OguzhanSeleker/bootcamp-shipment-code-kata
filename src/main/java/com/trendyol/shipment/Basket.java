package com.trendyol.shipment;

import com.trendyol.shipment.exceptions.ProductSizeInBasketCanNotBeGreaterThanFiveException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {
    private static final int MAX_PRODUCT_SIZE = 5;
    private static final int THRESHOLD = 3;
    private List<Product> products;

    public ShipmentSize getShipmentSize() throws ProductSizeInBasketCanNotBeGreaterThanFiveException {
        CheckProductMaxSizeFive(this.products.size());

        if (isProductListSizeGreaterOrEqualThreshold()) {
            if (isEveryProductInListSameShipmentSize())
                return FindLargestShipmentSizeInProducts().GetNextLargerShipmentSize();

            for (ShipmentSize size : ShipmentSize.values()) {
                if (ProductShipmentSizeMapGroupByShipmentSize().containsKey(size) && ProductShipmentSizeMapGroupByShipmentSize().get(size) >= THRESHOLD) {
                    return size.GetNextLargerShipmentSize();
                }
            }
        }
        return FindLargestShipmentSizeInProducts();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    private Map<ShipmentSize, Long> ProductShipmentSizeMapGroupByShipmentSize(){
        return this.getProducts().stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));
    }
    private boolean isProductListSizeGreaterOrEqualThreshold() {
        return this.products.size() >= THRESHOLD;
    }

    private void CheckProductMaxSizeFive(int size)
            throws ProductSizeInBasketCanNotBeGreaterThanFiveException {
        if (size > MAX_PRODUCT_SIZE)
            throw new ProductSizeInBasketCanNotBeGreaterThanFiveException();
    }

    private ShipmentSize FindLargestShipmentSizeInProducts() {
        return this.getProducts().stream()
                .max(Comparator.comparing(Product::getSize)).map(Product::getSize).orElse(ShipmentSize.SMALL);
    }

    private boolean isEveryProductInListSameShipmentSize() {
        return this.products.size() == this.products.stream().filter(i -> i.getSize() == FindLargestShipmentSizeInProducts()).count();
    }
}

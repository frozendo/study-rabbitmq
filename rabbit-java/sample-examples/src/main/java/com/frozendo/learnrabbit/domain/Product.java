package com.frozendo.learnrabbit.domain;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
public class Product implements Serializable {

    private Integer quantity;
    private String name;
    private String department;
    private BigDecimal price;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(quantity, that.quantity) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, name, price);
    }

    @Override
    public String toString() {
        return "ProductProducer{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public byte[] toByteArray() {
        return SerializationUtils.serialize(this);
    }
}

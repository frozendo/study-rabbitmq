package com.frozendo.learnrabbit.domain;

import java.math.BigDecimal;

public class MessageDTO {

    private Integer quantity;
    private String name;
    private String department;
    private BigDecimal price;
    private ExchangeTypeEnum exchangeType;

    public Product extractProduct() {
        var product = new Product();
        product.setQuantity(this.quantity);
        product.setName(this.name);
        product.setPrice(this.price);
        product.setDepartment(this.department);
        return product;
    }

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

    public ExchangeTypeEnum getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(ExchangeTypeEnum exchangeType) {
        this.exchangeType = exchangeType;
    }
}

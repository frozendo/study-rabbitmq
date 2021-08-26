package com.frozendo.rabbit.multi.service;

import com.frozendo.rabbit.multi.domain.Product;

public interface ExchangeService {
    void sendMessage(Product product);

}

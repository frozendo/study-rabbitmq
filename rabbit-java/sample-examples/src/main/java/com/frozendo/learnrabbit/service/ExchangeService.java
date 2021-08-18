package com.frozendo.learnrabbit.service;

import com.frozendo.learnrabbit.domain.Product;

public interface ExchangeService {
    void sendMessage(Product product);
}

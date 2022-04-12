package com.example.demo.problems.codility.soldproductsaggregator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EURExchangeService implements ExchangeService {
    @Override
    public Optional<BigDecimal> rate(String currency) {
        double randomValue = Math.random() * 10;

        if(randomValue < 5) {
            return Optional.of(BigDecimal.valueOf(5));
        }
        else if (randomValue < 7) {
            return Optional.of(BigDecimal.valueOf(-1));
        }
        else {
            return Optional.empty();
        }
    }
}

package com.example.demo.problems.codility.soldproductsaggregator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EURExchangeService implements ExchangeService {
    @Override
    public Optional<BigDecimal> rate(String currency) {
        return Optional.of(BigDecimal.valueOf(5));
    }
}

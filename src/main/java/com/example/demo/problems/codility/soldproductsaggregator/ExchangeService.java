package com.example.demo.problems.codility.soldproductsaggregator;

import java.math.BigDecimal;
import java.util.Optional;

interface ExchangeService {
    Optional<BigDecimal> rate(String currency);
}
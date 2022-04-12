package com.example.demo.problems.codility.soldproductsaggregator;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class SoldProductsAggregate {
    List<SimpleSoldProduct> products;
    BigDecimal total;
}

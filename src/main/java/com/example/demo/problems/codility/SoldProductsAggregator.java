/*
While working on an e-commerce service, you need to build a simple aggregator for a report, which will hold sold products and a sum of their prices converted to EUR currency.
You're given a stream (java.util.stream.Stream) of SoldProduct objects. SoldProduct is defined as follows:
@Value
class SoldProduct {
  String name;
  BigDecimal price;
  String currency;
}

(@Value comes from lombok tool and  will make this class immutable - generate toString, equals and hashCode, make all fields private and final, add getters for all the fields and add single all arguments constructor).
Write a function, which will map the Stream<SoldProduct> to an instance of SoldProductsAggregate which is defined as follows:
@Value
class SoldProductsAggregate {
  List<SimpleSoldProduct> products;
  BigDecimal total;
}

and SimpleSoldProduct:
@Value
class SimpleSoldProduct {
  String name;
  BigDecimal price;
}

To convert price to EUR use EURExchangeService which implements ExchangeService:
interface ExchangeService {
  Optional<BigDecimal> rate(String currency);
}

Unfortunately EURExchangeService is a bit buggy and may return  some invalid values (like null, empty or negative). You should handle it by ignoring them.
If an argument you get as input to SoldProductsAggregate.aggregate() is null, empty Stream and so on - then you must not return null. In case of such argument, you must convert it to empty Stream before doing the mapping.

Test Conditions:
Compilation successful.

com.codility.SoldProductsAggregatorSpec - should return empty result for null stream
OK

com.codility.SoldProductsAggregatorSpec - should return empty result for empty stream
OK

com.codility.SoldProductsAggregatorSpec - should filter out products with null exchange rate
OK

com.codility.SoldProductsAggregatorSpec - should filter out products with empty exchange rate
OK

com.codility.SoldProductsAggregatorSpec - should filter out products with negative exchange rate
OK

com.codility.SoldProductsAggregatorSpec - should filter out products with lowercase currency code
OK

com.codility.SoldProductsAggregatorSpec - should filter out products with whitespace currency code
OK

com.codility.SoldProductsAggregatorSpec - every product on a list should be an instance of SimpleSoldProduct class
OK

com.codility.SoldProductsAggregatorSpec - there should be ten filtered products
OK

com.codility.SoldProductsAggregatorSpec - all filtered products shold have appropriate name
OK

com.codility.SoldProductsAggregatorSpec - should return correct total
OK

com.codility.SoldProductsAggregatorSpec - sum od all products prices shoukd be equal to toatl
OK

com.codility.SoldProductsAggregatorSpec - should return correct result for various products
OK
*/
package com.example.demo.problems.codility;

import com.example.demo.interfaces.Test;
import com.example.demo.problems.codility.soldproductsaggregator.*;
import com.example.demo.util.Printer;
import com.example.demo.util.ProblemRunner;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoldProductsAggregator implements Test {
    private static final String FULL_CLASS_NAME = MethodHandles.lookup().lookupClass().getName();

    private final String SOLD_PRODUCT_NAME = "SOLD_PRODUCT_NAME";
    private final String SOLD_PRODUCT_CURRENCY_EUR = "EUR";

    private final Map<Stream<SoldProduct>, SoldProductsAggregate> testCases = new HashMap() {{
        put(Stream.empty(), new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(null, new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.ZERO, SOLD_PRODUCT_CURRENCY_EUR)), new SoldProductsAggregate(new ArrayList(){{ add(new SimpleSoldProduct(SOLD_PRODUCT_NAME, BigDecimal.ZERO)); }}, BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, "")), new SoldProductsAggregate(new ArrayList(){{ add(new SimpleSoldProduct(SOLD_PRODUCT_NAME, BigDecimal.valueOf(50L))); }}, BigDecimal.valueOf(50L)));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, null)), new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, " " + SOLD_PRODUCT_CURRENCY_EUR + " ")), new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, " " + SOLD_PRODUCT_CURRENCY_EUR)), new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, SOLD_PRODUCT_CURRENCY_EUR + " ")), new SoldProductsAggregate(new ArrayList(), BigDecimal.ZERO));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.ONE, SOLD_PRODUCT_CURRENCY_EUR)), new SoldProductsAggregate(new ArrayList(){{ add(new SimpleSoldProduct(SOLD_PRODUCT_NAME, BigDecimal.valueOf(5L))); }}, BigDecimal.valueOf(5L)));
        put(Stream.of(new SoldProduct(SOLD_PRODUCT_NAME, BigDecimal.TEN, SOLD_PRODUCT_CURRENCY_EUR)), new SoldProductsAggregate(new ArrayList(){{ add(new SimpleSoldProduct(SOLD_PRODUCT_NAME, BigDecimal.valueOf(50L))); }}, BigDecimal.valueOf(50L)));
    }};

    private final EURExchangeService exchangeService = new EURExchangeService();

//    SoldProductsAggregator(EURExchangeService EURExchangeService) {
//        this.exchangeService = EURExchangeService;
//    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ProblemRunner.run(FULL_CLASS_NAME);
    }

    @Override
    public void test(){
        Printer.printBegin(this.getClass().getName());

        int counter = 1;
        MutableBoolean result = new MutableBoolean();
        List<Boolean> results = new ArrayList();

        for (Map.Entry<Stream<SoldProduct>, SoldProductsAggregate> testCase : testCases.entrySet()) {
            Printer.print(String.valueOf(counter++));
            aggregate(testCase.getKey(), testCase.getValue(), result);
            results.add(result.getValue());
        }

        Printer.printSuccess(results);
        Printer.printFail(results);
        Printer.printEnd(this.getClass().getName());
    }

    private SoldProductsAggregate aggregate(Stream<SoldProduct> products, SoldProductsAggregate answer, MutableBoolean result){
        if(products == null) {
            products = Stream.empty();
        }

        List<SoldProduct> soldProduct = products.collect(Collectors.toList());

        Stream<SoldProduct> validatedProductsSupplier = validateProducts(soldProduct);

        List<SimpleSoldProduct> simpleSoldProducts = validatedProductsSupplier.map(this::createSimpleSoldProducts).collect(Collectors.toList());

        simpleSoldProducts.removeIf(Objects::isNull);

        SoldProductsAggregate soldProductsAggregate = new SoldProductsAggregate(simpleSoldProducts,
                simpleSoldProducts.stream().map(sp -> sp.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));

        final boolean IS_TEST_OK = soldProductsAggregate.equals(answer);

        Printer.print(MessageFormat.format("SoldProduct: {0} | SoldProductsAggregate: {1} | Answer: {2} | {3}",
                soldProduct,
                soldProductsAggregate,
                answer,
                IS_TEST_OK));

        result.setValue(IS_TEST_OK);

        return soldProductsAggregate;
    }

    private SimpleSoldProduct createSimpleSoldProducts(SoldProduct soldProduct){
        Optional<BigDecimal> optionalRate = exchangeService.rate(soldProduct.getCurrency());

        BigDecimal rate;

        if(optionalRate.isPresent() && optionalRate.get().compareTo(BigDecimal.ZERO) >= 0){
            rate = optionalRate.get();
        }
        else {
            return null;
        }

        return new SimpleSoldProduct(soldProduct.getName(), soldProduct.getPrice().multiply(rate));
    }

    private Stream<SoldProduct> validateProducts(List<SoldProduct> soldProducts) {
        if(soldProducts == null || !soldProducts.stream().findAny().isPresent()) {
            return Stream.empty();
        }

        soldProducts = soldProducts.stream().filter(p -> Objects.nonNull(p.getCurrency())).collect(Collectors.toList());

        soldProducts = soldProducts.stream().filter(p -> p.getCurrency().trim().length() == p.getCurrency().length()).collect(Collectors.toList());

        soldProducts = soldProducts.stream().filter(p -> p.getCurrency().toUpperCase().equals(p.getCurrency())).collect(Collectors.toList());

        return soldProducts.stream();
    }
}

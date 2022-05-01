package test;

import javafx.util.Pair;
import model.Cart;
import model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCart {
    //Responsabile Testing: Simone Nicosanti ; Matricola 0266910

    private static final List<Pair<Integer,Double>> PRODUCT_QUANTITY_PRICE = List.of(new Pair<>(2, 25.05), new Pair<>(4, 12.32), new Pair<>(1,24.33), new Pair<>(3,72.167)) ;

    private static final Double TEST_DELTA = 0.001 ;


    @Test
    public void testCartGetPriceCorrectResult() {
        Cart testCart = createTestCart() ;

        Double cartPrice = testCart.getPrice() ;

        Double testPrice = 0.0 ;
        for (Pair<Integer,Double> rowPair : PRODUCT_QUANTITY_PRICE) {
            testPrice = testPrice + rowPair.getKey() * rowPair.getValue() ;
        }

        assertEquals(testPrice, cartPrice, TEST_DELTA) ;
    }

    public static Cart createTestCart() {
        Cart testCart = new Cart() ;

        int i = 0 ;
        for (Pair<Integer, Double> productRowCouple : PRODUCT_QUANTITY_PRICE) {
            Product product = createTestProduct(i, productRowCouple.getValue()) ;
            int quantity = productRowCouple.getKey() ;
            for (int j = 0 ; j < quantity ; j++) testCart.insertProduct(product);
            i++ ;
        }
        return testCart ;
    }


    private static Product createTestProduct(Integer isbn, Double price) {
        Product product = new Product() ;
        product.setIsbn(isbn);
        product.setPrice(price);

        return product ;
    }
}

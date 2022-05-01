package engineering.dao;

import model.Cart;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CartFileSaver {

    private final String cartFileName ;

    public CartFileSaver(String userName) {
        cartFileName = userName + "_CART" ;
    }

    public Cart loadCartFromFile() {
        Cart cart = new Cart() ;
        try(
                FileInputStream fileInputStream = new FileInputStream(cartFileName);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ) {
            cart = (Cart) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            //If the file doesn't contain a previous cart, then is given back a new Cart instance
        }
        return cart ;
    }

    public void saveCartInFile(Cart cart) {
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(cartFileName);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeObject(cart);
        } catch (IOException ignored) {
            //If the file isn't found, then the previous Cart isn't saved
        }
    }

    public void deleteCartFromFile() {
        try {
            Files.delete(Path.of(cartFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

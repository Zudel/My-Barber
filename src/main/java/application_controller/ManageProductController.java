package application_controller;

import engineering.bean.UserBean;
import engineering.bean.ProductBean;
import engineering.dao.ProductDAO;
import engineering.exception.DuplicatedProductException;
import engineering.exception.InvalidInsertProductException;
import model.Barber;
import model.Product;
import engineering.container.ProductCatalog;
import java.util.List;
import java.util.Objects;

public class ManageProductController {

    public void addProduct(ProductBean productBean, UserBean userBean) throws DuplicatedProductException, InvalidInsertProductException {


        ProductDAO productDAO = new ProductDAO();
        ProductCatalog productCatalog = productDAO.loadAllProductsByBarberEmail(userBean.getUserEmail());
        Barber barber = new Barber(userBean.getUserEmail(), userBean.getPass(), userBean.getName(), userBean.getSurname());

        Product product = new Product(productBean.getBeanIsbn(), productBean.getBeanName(), productBean.getBeanDescription(), productBean.getBeanPrice(), barber);
        product.setVendor(barber);
        List<Product> products = productCatalog.filterByName(product.getName());

        for(Product localProduct : products) {

            if(controlDuplicatedProduct(localProduct, product)) {
                throw new DuplicatedProductException();
            }
        }

        productCatalog.addProduct(product);

        if(!productDAO.insertProduct(product, userBean.getUserEmail())) {

            throw new InvalidInsertProductException();

        }

    }

    private boolean controlDuplicatedProduct(Product localProduct, Product product) {

        return (Objects.equals(localProduct.getDescription(), product.getDescription())) && (Objects.equals(localProduct.getPrice(), product.getPrice()));

    }

}

import _02_salesDatabase.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

//        Sale sale = new Sale();
//        sale.setDate(new Date(20230628));
//
//        Product product = new Product();
//        product.setName("testProduct");
//        product.setPrice(BigDecimal.TEN);
//        product.setQuantity(5);
//
//        product.getSales().add(sale);
//        sale.setProduct(product);
//
//        entityManager.persist(product);
//
//        Product found = entityManager.find(Product.class, 2L);
//        entityManager.remove(found);

        entityManager.getTransaction().commit();

    }
}

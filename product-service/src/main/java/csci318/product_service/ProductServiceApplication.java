package csci318.product_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import csci318.product_service.model.Product;
import csci318.product_service.repository.ProductRepository;
import csci318.product_service.service.ProductService;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(ProductServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadDatabase(ProductRepository productRepository, ProductService productService) throws Exception {
		return args -> {

			// Example object 1
			Product entry = new Product();
			entry.setName("Apple");
			entry.setCategory("FRUIT");
			entry.setPrice(2);
			entry.setWeight(.500);
			productService.addProduct(entry);

			// Example object 2
			Product entry1 = new Product();
			entry1.setName("Bannana");
			entry1.setCategory("FRUIT");
			entry1.setPrice(3);
			entry1.setWeight(.700);
			productService.addProduct(entry1);
		};
	}

}

package csci318.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//import csci318.demo.model.Users.Customer;
import csci318.demo.repository.CustomerRepository;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(CustomerRepository customerRepository) throws Exception {
		return args -> {

			//Uncomment to include sample customers without requiring registering
			/* 
			Customer entry = new Customer();
			entry.setId(1L);
			entry.setName("Ranko");
			entry.setEmail("rkp@uowmail.edu.au");
			entry.setPassword("Password");
			entry.setAge(23);
			entry.setAddress("Wollongong");
			entry.setGender("MALE");
			customerRepository.save(entry);


			Customer entry1 = new Customer();
			entry1.setId(2L);
			entry1.setName("Poh");
			entry.setEmail("poh@uowmail.edu.au");
			entry.setPassword("Password");
			entry.setAge(28);
			entry.setAddress("Wollongong");
			entry.setGender("FEMALE");
			customerRepository.save(entry1);
			*/
			
		};
	}

}

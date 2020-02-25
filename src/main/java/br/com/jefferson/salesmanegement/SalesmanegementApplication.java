package br.com.jefferson.salesmanegement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.jefferson.salesmanegement.domain.models.User;
import br.com.jefferson.salesmanegement.domain.repository.UserRepository;
import br.com.jefferson.salesmanegement.services.UserService;

@SpringBootApplication
public class SalesmanegementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesmanegementApplication.class, args);
	}
}

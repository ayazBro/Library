package ayaz.bro.library;

import ayaz.bro.library.models.Author;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LibraryApplication {
	public static void main(String[] args) {SpringApplication.run(LibraryApplication.class, args);}
}

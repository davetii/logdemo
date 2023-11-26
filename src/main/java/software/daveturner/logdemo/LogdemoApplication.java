package software.daveturner.logdemo;


import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogdemoApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(LogdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Hello World");
		System.exit(0);
	}
}

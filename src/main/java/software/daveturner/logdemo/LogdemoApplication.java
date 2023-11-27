package software.daveturner.logdemo;


import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

@SpringBootApplication
public class LogdemoApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(LogdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LogdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Hello World from " + logger.getName() + " using " + logger.getClass());
		File file = ResourceUtils.getFile("classpath:sample.json");
		ObjectMapper mapper = new ObjectMapper();
		String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
		List<PersonInfo> list = mapper.readValue(content, new TypeReference<List<PersonInfo>>(){} );
		for(PersonInfo p : list) {
			logger.info(mapper.writeValueAsString(p));
		}
		//System.exit(0);
	}
}

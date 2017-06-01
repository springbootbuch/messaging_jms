package de.springbootbuch.messaging_jms.simple;

import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	private final JmsTemplate jmsTemplate;

	public Application(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		final String greeting = Stream.of(args)
			.findFirst().orElse("Hello from Main");
		this.jmsTemplate.send("greetings-topic", session -> 
			session.createTextMessage(greeting)
		);
	}
}
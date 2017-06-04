package de.springbootbuch.messing_jms.film_rental;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	MappingJackson2MessageConverter filmReturnedEventConverter() {
		final MappingJackson2MessageConverter converter 
			= new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("eventType");
		final Map<String, Class<?>> t = new HashMap<>();
		t.put("FilmReturnedEvent", FilmReturnedEvent.class);
		converter.setTypeIdMappings(t);
		return converter;
	}
}

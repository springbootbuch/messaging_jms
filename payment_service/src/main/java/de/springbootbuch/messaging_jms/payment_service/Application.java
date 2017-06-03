package de.springbootbuch.messaging_jms.payment_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ObjectMapper objectMapper(final JsonComponentModule jsonComponentModule) {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(jsonComponentModule);
		return objectMapper;
	}

	@Bean
	FilmReturnedEventReceiver filmReturnedEventReceiver() {
		return new FilmReturnedEventReceiver();
	}

	@Bean
	MessageConverter filmReturnedEventConverter(final ObjectMapper jacksonObjectMapper) {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(jacksonObjectMapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("eventType");
		final Map<String, Class<?>> f = new HashMap<>();
		f.put("FilmReturnedEvent", FilmReturnedEvent.class);
		converter.setTypeIdMappings(f);
		return converter;
	}

	@Bean
	MessageListenerAdapter filmReturnedEventListener(
		final MessageConverter filmReturnedEventConverter,
		final FilmReturnedEventReceiver filmReturnedEventReceiver
	) {
		final MessageListenerAdapter adapter
			= new MessageListenerAdapter(
				filmReturnedEventReceiver);
		adapter
			.setDefaultListenerMethod("filmReturned");
		adapter
			.setMessageConverter(filmReturnedEventConverter);
		return adapter;
	}

	@Bean
	JmsListenerConfigurer jmsListenerConfigurer(
		MessageListenerAdapter filmReturnedEventListener
	) {
		return registrar -> {
			final SimpleJmsListenerEndpoint rv
				= new SimpleJmsListenerEndpoint();
			rv.setId("returned-films-receiver");
			rv.setMessageListener(filmReturnedEventListener);
			rv.setDestination("returned-films-events");

			registrar.registerEndpoint(rv);
		};
	}
}

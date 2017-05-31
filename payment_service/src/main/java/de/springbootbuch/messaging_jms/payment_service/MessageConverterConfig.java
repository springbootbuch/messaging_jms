package de.springbootbuch.messaging_jms.payment_service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Part of springbootbuch.de.
 * 
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
public class MessageConverterConfig {
	
	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		 MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("eventType");
		final Map<String, Class<?>> f = new HashMap<>();
		f.put("FilmReturnedEvent", FilmReturnedEventReceiver.FilmReturnedEvent.class);
		converter.setTypeIdMappings(f);
		return converter;
	}
}

package de.springbootbuch.messing_jms.film_rental;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Part of springbootbuch.de.
 * <br>
 * You can choose whatever converter you want. This example uses a neutral
 * format on purpose.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
public class MessageConverterConfig {

	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		final MappingJackson2MessageConverter converter 
			= new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("eventType");
		final Map<String, Class<?>> f = new HashMap<>();
		f.put("FilmReturnedEvent", FilmReturnedEvent.class);
		converter.setTypeIdMappings(f);
		return converter;
	}
}
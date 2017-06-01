package de.springbootbuch.messaging_jms.simple;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.destination.DestinationResolver;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Profile("destination-router")
@Configuration
public class DestinationResolverConfig {

	@Bean
	public DestinationResolver destinationResolver() {
		return (session, destinationName, pubSubDomain) -> {
			if (destinationName.endsWith("queue")) {
				return session.createQueue(destinationName);
			} else if (destinationName.endsWith("topic")) {
				return session.createTopic(destinationName);
			}
			throw new RuntimeException(
				"Invalid destination: " + destinationName);
		};
	}
}
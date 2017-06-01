package de.springbootbuch.messaging_jms.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class AnnotedGreetingListener {
	
	private static final Logger LOG = LoggerFactory
		.getLogger(AnnotedGreetingListener.class);
	
	@JmsListener(destination = "greetings-topic")
	@SendTo("responses-queue")
	public String onGreeting(final String greeting) {
		LOG.info("Received greeting {}", greeting);
		return "Hello from Spring";
	}
}

package de.springbootbuch.messaging_jms.simple;

import org.springframework.jms.core.JmsTemplate;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class GreetingService {

	private final JmsTemplate jmsTemplate;

	public GreetingService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendGreeting(final String greeting) {
		this.jmsTemplate.send("greetings-topic", session
			-> session.createTextMessage(greeting)
		);
	}
}

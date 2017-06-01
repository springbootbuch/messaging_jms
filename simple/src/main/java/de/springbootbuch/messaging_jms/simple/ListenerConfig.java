package de.springbootbuch.messaging_jms.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Configuration
public class ListenerConfig 
	implements JmsListenerConfigurer {

	@Override
	public void configureJmsListeners(
		JmsListenerEndpointRegistrar registrar
	) {
		final MessageListenerAdapter adapter
			= new MessageListenerAdapter(
				new GreetingListener());
		adapter
			.setDefaultListenerMethod("onGreeting");
		adapter
			.setDefaultResponseQueueName("responses-queue");

		final SimpleJmsListenerEndpoint rv 
			= new SimpleJmsListenerEndpoint();
		rv.setId("greetingsEndpoint");
		rv.setMessageListener(adapter);
		rv.setDestination("greetings-topic");

		registrar.registerEndpoint(rv);
	}
	
	public static class GreetingListener {

		private static final Logger LOG = LoggerFactory
			.getLogger(GreetingListener.class);

		public String onGreeting(final String greeting) {
			LOG.info("Received greeting {}", greeting);
			return "Hello from Spring";
		}
	}
}
package de.springbootbuch.messaging_jms.simple;

import javax.jms.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Profile("distinct-infrastructure")
@Configuration
public class DistinctInfrastructureConfig {

	@Bean
	public JmsListenerContainerFactory<?> topicContainerFactory(
		DefaultJmsListenerContainerFactoryConfigurer configurer,
		ConnectionFactory connectionFactory
	) {
		DefaultJmsListenerContainerFactory containerFactory
			= new DefaultJmsListenerContainerFactory();

		configurer
			.configure(containerFactory, connectionFactory);
		containerFactory.setPubSubDomain(true);
		return containerFactory;
	}

	@Bean
	public JmsListenerContainerFactory<?> queueContainerFactory(
		DefaultJmsListenerContainerFactoryConfigurer configurer,
		ConnectionFactory connectionFactory
	) {
		DefaultJmsListenerContainerFactory containerFactory
			= new DefaultJmsListenerContainerFactory();

		configurer.configure(containerFactory, connectionFactory);
		return containerFactory;
	}

	@Bean
	public JmsTemplate topicJmsTemplate(
		ConnectionFactory connectionFactory
	) {
		JmsTemplate jmsTemplate 
			= new JmsTemplate(connectionFactory);
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}

	@Bean
	public JmsTemplate queueJmsTemplate(
		ConnectionFactory connectionFactory
	) {
		return new JmsTemplate(connectionFactory);
	}
	
	@Bean
	public GreetingService greetingService(final JmsTemplate topicJmsTemplate) {
		return new GreetingService(topicJmsTemplate);
	}

	@Bean
	AnnotedGreetingListenerAlt annotedGreetingListenerAlt() {
		return new AnnotedGreetingListenerAlt();
	}
	
	static class AnnotedGreetingListenerAlt {

		private static final Logger LOG = LoggerFactory
			.getLogger(AnnotedGreetingListenerAlt.class);

		@JmsListener(
			destination = "greetings-topic",
			containerFactory = "topicContainerFactory"
		)
		public void onGreeting(final String greeting) {
			LOG.info("Received greeting {}", greeting);
		}
	}
}

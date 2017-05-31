package de.springbootbuch.messaging_jms.payment_service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class FilmReturnedEventReceiver {

	private static final Logger LOG = LoggerFactory
		.getLogger(Application.class);

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FilmReturnedEvent {

		private final String title;

		public FilmReturnedEvent(
			@JsonProperty("title") String title
		) {
			this.title = title;
		}
	}

	@JmsListener(destination = "returned-films-events")
	public void filmReturned(FilmReturnedEvent event) throws JMSException {
		LOG.info(
			"Film '{}' returned, billing customer...", 
			event.title
		);
	}
}

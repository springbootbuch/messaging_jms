package de.springbootbuch.messaging_jms.simple;

import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner{
	
	private final GreetingService greetingService;

	public CommandLineRunnerImpl(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		final String greeting = Stream.of(args)
			.findFirst().orElse("Hello from Main");
		this.greetingService.sendGreeting(greeting);
	}
}

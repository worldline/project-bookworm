package worldline.bookworm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entrypoint for the example application.
 * This wil start the REST server.
 * <br/><br/>
 *
 * The port that is bound is defined in application.properties (see resources folder).
 */
@SpringBootApplication
public class BookwormApplication
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BookwormApplication.class);

	public static void main(String[] args)
	{
		LOGGER.info("Starting project bookworm");
		SpringApplication.run(BookwormApplication.class, args);
	}
}

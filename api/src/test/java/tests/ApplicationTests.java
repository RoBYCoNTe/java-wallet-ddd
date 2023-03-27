package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import it.robertoconterosito.wallet.Application;

@SpringBootTest(classes = Application.class)
@EnableWebMvc
@ComponentScan(basePackages = {
		"it.robertoconterosito.wallet",
		"it.robertoconterosito.wallet.data",
})
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}

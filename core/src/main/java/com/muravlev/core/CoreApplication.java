package com.muravlev.core;

import com.muravlev.core.controllers.ChannelController;
import com.muravlev.core.controllers.PostController;
import com.muravlev.core.controllers.UserController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Информация",
				version = "1.0.0",
				description = "Описание информации",
				termsOfService = "Муравлёв",
				contact = @Contact(
						name = "Dmitry",
						email = "dmur1991@gmail.com"
				),
				license = @License(
						name = "MURAVLEV",
						url = "ya.ru"
				)
		)
)
public class CoreApplication {

	@Autowired
	private UserController userController;

	@Autowired
	private ChannelController channelController;

	@Autowired
	private PostController postController;


//	public static void main(String[] args) {
//		ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
//
//		UserController userController = context.getBean(UserController.class);
//		ChannelController channelController = context.getBean(ChannelController.class);
//		PostController postController = context.getBean(PostController.class);
//
//		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
//		ApplicationSimulator simulator = new ApplicationSimulator(userController, channelController, postController, executorService);
//		simulator.simulate();
//	}
	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}

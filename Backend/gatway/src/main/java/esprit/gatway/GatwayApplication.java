package esprit.gatway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatwayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatwayApplication.class, args);
	}

	@Bean
	public org.springframework.cloud.gateway.route.RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("microservice5",
						r -> r.path("/program/**")
								.uri("lb://microservice5")) 
      .route("microservice6",
						r -> r.path("/produit/**")
								.uri("lb://microservice6"))
				.route("microservice2",
						r -> r.path("/commande/**")
								.uri("lb://microservice2"))
          .route("microservice4",
              r -> r.path("/seance/**")
                  .uri("lb://microservice4"))
				.route("microservice1",
						r -> r.path("/event/**")
								.uri("lb://microservice1"))
				.route("microservice3",
						r -> r.path("/reclamation/**")
								.uri("lb://microservice3"))
				.build();

	}
	}

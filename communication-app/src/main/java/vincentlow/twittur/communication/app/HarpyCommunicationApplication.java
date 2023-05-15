package vincentlow.twittur.communication.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"vincentlow.twittur.communication.*"})
@EntityScan(basePackages = {"vincentlow.twittur.communication.entity.*"})
@EnableJpaRepositories(basePackages = {"vincentlow.twittur.communication.*"})
@EnableFeignClients(basePackages = {"vincentlow.twittur.communication.*"})
public class HarpyCommunicationApplication {

  public static void main(String[] args) {

    SpringApplication.run(HarpyCommunicationApplication.class);
  }
}

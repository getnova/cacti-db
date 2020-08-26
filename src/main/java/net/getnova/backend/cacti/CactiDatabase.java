package net.getnova.backend.cacti;

import lombok.extern.slf4j.Slf4j;
import net.getnova.backend.api.handler.websocket.WebsocketApiService;
import net.getnova.backend.boot.module.Module;
import net.getnova.backend.sql.JpaModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@Slf4j
@ComponentScan
@Module({JpaModule.class, WebsocketApiService.class})
@EnableJpaRepositories
@EnableTransactionManagement
public class CactiDatabase {

  private final JpaModule jpaModule;

  public CactiDatabase(final JpaModule jpaModule) {
    this.jpaModule = jpaModule;
  }

  @PostConstruct
  private void postConstruct() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

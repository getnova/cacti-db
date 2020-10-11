package net.getnova.backend.module.cacti;

import net.getnova.backend.api.ApiModule;
import net.getnova.backend.boot.module.Module;
import net.getnova.backend.jpa.JpaModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@Module({JpaModule.class, ApiModule.class})
@EnableJpaRepositories
@EnableTransactionManagement
public class CactiDatabase {
}

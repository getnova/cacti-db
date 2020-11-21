package net.getnova.module.cacti;

import net.getnova.framework.api.ApiModule;
import net.getnova.framework.boot.module.Module;
import net.getnova.framework.jpa.JpaModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan
@Module({JpaModule.class, ApiModule.class})
@EnableJpaRepositories
@EnableTransactionManagement
public class CactiDatabase {
}

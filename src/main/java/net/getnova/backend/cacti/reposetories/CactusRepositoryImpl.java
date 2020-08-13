package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.sql.repository.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class CactusRepositoryImpl extends SqlRepositoryImpl<Cactus, UUID> implements CactusRepository {

  public CactusRepositoryImpl() {
    super(Cactus.class);
  }
}

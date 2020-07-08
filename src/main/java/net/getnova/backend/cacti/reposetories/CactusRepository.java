package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.sql.reposetory.SqlRepository;

import java.util.UUID;

public interface CactusRepository extends SqlRepository<Cactus, UUID> {
}

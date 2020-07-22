package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CactusState;
import net.getnova.backend.sql.repository.SqlRepository;

import java.util.UUID;

public interface CactusStateRepository extends SqlRepository<CactusState, UUID> {
}

package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CactusAcquisition;
import net.getnova.backend.sql.repository.SqlRepository;

import java.util.UUID;

public interface CactusAcquisitionRepository extends SqlRepository<CactusAcquisition, UUID> {
}

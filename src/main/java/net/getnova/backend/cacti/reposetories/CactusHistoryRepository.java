package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CactusHistory;
import net.getnova.backend.sql.reposetory.SqlRepository;

import java.util.List;
import java.util.UUID;

public interface CactusHistoryRepository extends SqlRepository<CactusHistory, UUID> {

    List<CactusHistory> list(Cactus cactus);
}

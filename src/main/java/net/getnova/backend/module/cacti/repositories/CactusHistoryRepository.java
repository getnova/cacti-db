package net.getnova.backend.module.cacti.repositories;

import net.getnova.backend.module.cacti.models.Cactus;
import net.getnova.backend.module.cacti.models.CactusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CactusHistoryRepository extends JpaRepository<CactusHistory, UUID> {

  List<CactusHistory> findAllByCactusOrderByTimestamp(Cactus cactus);
}

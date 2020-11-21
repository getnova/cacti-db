package net.getnova.module.cacti.repository;

import net.getnova.module.cacti.model.Cactus;
import net.getnova.module.cacti.model.CactusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CactusHistoryRepository extends JpaRepository<CactusHistory, UUID> {

  List<CactusHistory> findAllByCactusOrderByTimestamp(Cactus cactus);
}

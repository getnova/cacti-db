package net.getnova.backend.module.cacti.repositories;

import net.getnova.backend.module.cacti.models.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareGroupRepository extends JpaRepository<CareGroup, String> {

  List<CareGroup> findByOrderById();
}

package net.getnova.module.cacti.repository;

import net.getnova.module.cacti.model.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareGroupRepository extends JpaRepository<CareGroup, String> {

  List<CareGroup> findAllByOrderById();
}

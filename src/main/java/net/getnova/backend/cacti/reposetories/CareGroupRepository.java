package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareGroupRepository extends JpaRepository<CareGroup, String> {
}

package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormRepository extends JpaRepository<Form, UUID> {

    List<Form> findByOrderByName();
}

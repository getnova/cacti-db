package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.sql.reposetory.SqlRepository;

import java.util.UUID;

public interface FormRepository extends SqlRepository<Form, UUID> {
}

package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Form;
import net.getnova.backend.sql.repository.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class FormRepositoryImpl extends SqlRepositoryImpl<Form, UUID> implements FormRepository {

  public FormRepositoryImpl() {
    super(Form.class);
  }
}

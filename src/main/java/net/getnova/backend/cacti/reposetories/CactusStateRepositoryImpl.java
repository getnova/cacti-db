package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CactusState;
import net.getnova.backend.sql.repository.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class CactusStateRepositoryImpl extends SqlRepositoryImpl<CactusState, UUID> implements CactusStateRepository {

    public CactusStateRepositoryImpl() {
        super(CactusState.class);
    }
}

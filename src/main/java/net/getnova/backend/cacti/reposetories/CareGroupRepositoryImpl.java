package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CareGroup;
import net.getnova.backend.sql.repository.SqlRepositoryImpl;

import javax.inject.Singleton;

@Singleton
public final class CareGroupRepositoryImpl extends SqlRepositoryImpl<CareGroup, String> implements CareGroupRepository {

    public CareGroupRepositoryImpl() {
        super(CareGroup.class);
    }
}

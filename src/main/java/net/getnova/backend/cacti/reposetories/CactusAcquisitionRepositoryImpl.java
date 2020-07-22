package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.CactusAcquisition;
import net.getnova.backend.sql.repository.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class CactusAcquisitionRepositoryImpl extends SqlRepositoryImpl<CactusAcquisition, UUID> implements CactusAcquisitionRepository {

    public CactusAcquisitionRepositoryImpl() {
        super(CactusAcquisition.class);
    }
}

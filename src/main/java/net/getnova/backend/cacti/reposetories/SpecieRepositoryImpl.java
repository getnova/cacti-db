package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Specie;
import net.getnova.backend.sql.reposetory.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class SpecieRepositoryImpl extends SqlRepositoryImpl<Specie, UUID> implements SpecieRepository {

    public SpecieRepositoryImpl() {
        super(Specie.class);
    }
}

package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Genus;
import net.getnova.backend.sql.reposetory.SqlRepositoryImpl;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public final class GenusRepositoryImpl extends SqlRepositoryImpl<Genus, UUID> implements GenusRepository {

    public GenusRepositoryImpl() {
        super(Genus.class);
    }
}

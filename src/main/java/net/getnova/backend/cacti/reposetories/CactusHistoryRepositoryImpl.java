package net.getnova.backend.cacti.reposetories;

import net.getnova.backend.cacti.models.Cactus;
import net.getnova.backend.cacti.models.CactusHistory;
import net.getnova.backend.sql.SqlService;
import net.getnova.backend.sql.reposetory.SqlRepositoryImpl;
import org.hibernate.Session;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

@Singleton
public final class CactusHistoryRepositoryImpl extends SqlRepositoryImpl<CactusHistory, UUID> implements CactusHistoryRepository {

    @Inject
    private SqlService sqlService;

    public CactusHistoryRepositoryImpl() {
        super(CactusHistory.class);
    }

    @Override
    public List<CactusHistory> list(final Cactus cactus) {
        try (Session session = this.sqlService.openSession()) {
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<CactusHistory> criteriaQuery = criteriaBuilder.createQuery(CactusHistory.class);
            final Root<CactusHistory> root = criteriaQuery.from(CactusHistory.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("cactus").get("id"), cactus.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}

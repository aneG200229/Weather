package org.aneg.dao;

import lombok.RequiredArgsConstructor;
import org.aneg.model.SessionModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionDaoImpl implements SessionDao {
    private final SessionFactory sessionFactory;

    @Override
    public void save(SessionModel sessionModel) {
        sessionFactory.getCurrentSession().persist(sessionModel);
    }

    @Override
    public Optional<SessionModel> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Query<SessionModel> query = session.createQuery("FROM SessionModel WHERE id = :id"
                , SessionModel.class);
        return query.setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public void deleteById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Query<SessionModel> query = session.createQuery("DELETE SessionModel WHERE id = :id");
        query.setParameter("id", id).executeUpdate();
    }

}

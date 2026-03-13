package org.aneg.dao;

import lombok.RequiredArgsConstructor;
import org.aneg.model.Location;
import org.aneg.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationDaoImpl implements LocationDao {
    private final SessionFactory sessionFactory;

    @Override
    public void save(Location location) {
        sessionFactory.getCurrentSession().persist(location);
    }

    @Override
    public List<Location> findByUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Location> query = session.createQuery("FROM Location WHERE user.id = :id", Location.class);
        return query.setParameter("id", user.getId()).list();
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("DELETE Location WHERE id = :id")
                .setParameter("id", id).executeUpdate();
    }
}

package org.aneg.dao;

import org.aneg.model.Location;
import org.aneg.model.User;

import java.util.List;

public interface LocationDao {
    void save(Location location);

    List<Location> findByUser(User user);

    void deleteById(Long id);
}

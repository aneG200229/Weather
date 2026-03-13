package org.aneg.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aneg.dao.LocationDao;
import org.aneg.model.Location;
import org.aneg.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationDao dao;

    @Transactional
    public void saveLocation(User user, String name, BigDecimal lat, BigDecimal lon) {
        Location location = new Location();
        location.setUser(user);
        location.setLatitude(lat);
        location.setLongitude(lon);
        location.setName(name);

        dao.save(location);
        log.info("Location {} save", name);
    }

    @Transactional
    public List<Location> getLocations(User user) {
        return dao.findByUser(user);
    }

    @Transactional
    public void deleteLocation(Long id) {
        dao.deleteById(id);
    }
}

package org.aneg.dao;

import org.aneg.model.SessionModel;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao {
    void save(SessionModel session);

    Optional<SessionModel> findById(UUID id);

    void deleteById(UUID id);
}

package org.shinmen.geektrust.repository;

import java.util.Map;

import org.shinmen.geektrust.models.Match;
import org.shinmen.geektrust.repository.interfaces.IMatchRepository;

public class MatchRepository extends GenericRepository<Match> implements IMatchRepository {
    public MatchRepository(Map<String, Match> matchMap) {
        super(matchMap);
    }
}

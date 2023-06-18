package com.ridesharing.geektrust.repository;

import java.util.Map;

import com.ridesharing.geektrust.models.Match;
import com.ridesharing.geektrust.repository.interfaces.IMatchRepository;

public class MatchRepository extends GenericRepository<Match> implements IMatchRepository {
    public MatchRepository(Map<String, Match> matchMap) {
        super(matchMap);
    }
}

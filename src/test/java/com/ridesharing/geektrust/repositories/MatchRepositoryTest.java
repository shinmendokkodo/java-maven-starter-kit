package com.ridesharing.geektrust.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ridesharing.geektrust.models.Match;
import com.ridesharing.geektrust.repository.MatchRepository;
import com.ridesharing.geektrust.repository.interfaces.IMatchRepository;

class MatchRepositoryTest {

    private IMatchRepository matchRepository;

    @BeforeEach
    void setup() {
        Map<String, Match> matchMap = new HashMap<>();
        matchMap.put("R1", new Match("R1", List.of("D1", "D2", "D3")));
        matchMap.put("R2", new Match("R2", List.of("D3", "D4", "D2")));
        matchMap.put("R3", new Match("R3", List.of("D1", "D3", "D4")));
        matchRepository = new MatchRepository(matchMap);
    }

    @Test
    void getById_GivenRiderId_ShouldReturnMatch() {
        // Arrange
        Match expectedMatch = new Match("R3", List.of("D1", "D3", "D4"));
        // Act
        Match actualMatch = matchRepository.getById("R3");
        // Assert
        Assertions.assertEquals(expectedMatch, actualMatch);
    }

    @Test
    void getById_GivenRiderId_ShouldReturnNull() {
        // Arrange
        String matchId = "R5";
        // Act
        Match match = matchRepository.getById(matchId);
        // Assert
        Assertions.assertNull(match);
    }

    @Test
    void save_ShouldSaveMatch() {
        // Arrange
        Match match = new Match("R4", List.of("D3", "D2", "D4"));
        // Act
        matchRepository.save(match);
        // Assert
        Assertions.assertEquals(match, matchRepository.getById("R4"));
        Assertions.assertEquals(4, matchRepository.getMap().size());
    }
}
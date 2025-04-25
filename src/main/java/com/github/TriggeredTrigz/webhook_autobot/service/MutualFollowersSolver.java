package com.github.TriggeredTrigz.webhook_autobot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.TriggeredTrigz.webhook_autobot.dto.User;

@Service
public class MutualFollowersSolver {
    
    public List<List<Integer>> findMutualFollowers(List<User> users) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Set<Integer>> followMap = new HashMap<>();

        // Build follows map
        for (User user : users) {
            followMap.put(user.getId(), new HashSet<>(user.getFollows()));
        }

        // Find mutual followers
        for (User user : users) {
            for (Integer followedId : user.getFollows()) {
                if (isMutualFollow(followMap, user.getId(), followedId)) {
                    addSortedPair(result, user.getId(), followedId);
                }
            }
        }

        result.sort(Comparator.comparingInt((List<Integer> pair) -> pair.get(0)).thenComparingInt(pair -> pair.get(1)));

        return result;
    }

    private boolean isMutualFollow(Map<Integer, Set<Integer>> followMap, 
                                    Integer userA, Integer userB) {
        return followMap.containsKey(userB) && 
               followMap.get(userB).contains(userA);
    }

    private void addSortedPair(List<List<Integer>> result, 
                                Integer userA, Integer userB) {
        List<Integer> pair = Arrays.asList(
            Math.min(userA, userB),
            Math.max(userA, userB)
        );
        if (!result.contains(pair)) {
            result.add(pair);
        }
    }

}

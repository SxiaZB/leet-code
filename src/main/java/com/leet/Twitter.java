package com.leet;

import java.util.*;

/**
 * @url https://leetcode-cn.com/problems/design-twitter/
 * @title 设计推特（355题）
 * <p>
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
public class Twitter {

    private Map<Integer, HashSet<Integer>> followMap;
    private List<Integer> tweetList;
    private Map<Integer, HashSet<Integer>> tweetListIndexMap;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        followMap = new HashMap<>();
        tweetList = new ArrayList<>();
        tweetListIndexMap = new HashMap<>();
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        tweetList.add(tweetId);
        HashSet<Integer> userTweetListIndexSet = tweetListIndexMap.computeIfAbsent(userId, k -> new HashSet<>());
        userTweetListIndexSet.add(tweetList.size() - 1);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> followeeIds = followMap.get(userId);
        if (followeeIds == null) {
            followeeIds = new HashSet<>();
        }
        List<Integer> feedList = new ArrayList<>();
        HashSet<Integer> myTwitterList = tweetListIndexMap.get(userId);
        if (myTwitterList != null) {
            feedList.addAll(myTwitterList);
        }
        for (Iterator<Integer> iterator = followeeIds.iterator(); iterator.hasNext(); ) {
            HashSet<Integer> followeeTwitterList = tweetListIndexMap.get(iterator.next());
            if (followeeTwitterList != null) {
                feedList.addAll(followeeTwitterList);
            }
        }
        feedList.sort((a, b) -> b - a);

        List<Integer> newsFeedList = new ArrayList<>(10);
        for (int i = 0; i < 10 && i < feedList.size(); i++) {
            newsFeedList.add(i, tweetList.get(feedList.get(i)));
        }
        return newsFeedList;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (followeeId == followerId) {
            return;
        }
        HashSet<Integer> followeeSet = followMap.computeIfAbsent(followerId, k -> new HashSet<>());
        followeeSet.add(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        HashSet<Integer> followeeSet = followMap.get(followerId);
        if (followeeSet != null) {
            followeeSet.remove(followeeId);
        }
    }
}

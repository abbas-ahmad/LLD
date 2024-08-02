package demos.ratelimiter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
* Features Required:
Configurable Limits: Ability to set request limits per unit of time.

Support for Different Time Windows: Support for different types of time windows (e.g., sliding window, fixed window).

Thread Safety: Ensure that the rate limiter can handle concurrent access correctly.

Ease of Configuration: Easily configurable limits and time windows.

Extensibility: Allow extension for different rate-limiting strategies.

Metrics and Logging: Provide metrics and logging for monitoring purposes.
*
*
* Design Patterns
Strategy Pattern: Allows for flexibility in changing rate-limiting algorithms without modifying the client code.

Singleton Pattern: Ensures only one instance of the rate limiter exists, maintaining a consistent state.

Factory Pattern: Encapsulates the creation logic for different rate-limiting strategies, promoting clean code and adherence to the Open/Closed principle.

Template Method Pattern: Provides a standard process for rate-limiting while allowing specific steps to be defined by subclasses.
*
*
*
* Multiple Algorithms :
Fixed Window Algorithm: Counts the number of requests in fixed time windows.

Sliding Window Algorithm: Uses a rolling time window to count requests.
*
* */
public class RateLimiterApp {
    public static void main(String[] args) {
        RateLimiter rateLimiterFixed = RateLimiterFactory.createRateLimiter("fixed", 10, 6);
        RateLimiter rateLimiterSliding = RateLimiterFactory.createRateLimiter("sliding", 10, 6);

        System.out.println("Fixed Rate Limiter...");
        for (int i = 0; i < 12; i++) {
            System.out.println(rateLimiterFixed.allowRequest("client1"));
        }

        System.out.println("Sliding Rate Limiter...");
        for (int i = 0; i < 12; i++) {
            System.out.println(rateLimiterSliding.allowRequest("client1"));
        }
    }
}

// 1. Rate limiter interface
interface RateLimiter{
    boolean allowRequest(String clientId);
}

class FixedWindowRL implements RateLimiter{

    long windowSize;
    long maxRequests;
    Map<String, Integer> reqCounts;
    Map<String, Long> windowStartTime;


    FixedWindowRL(long ws, long count){
        windowSize = ws;
        maxRequests = count;
        windowStartTime = new HashMap<>();
        reqCounts = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();
        windowStartTime.putIfAbsent(clientId, currentTime);
        reqCounts.putIfAbsent(clientId, 0);

        Long clientWindowStart = windowStartTime.get(clientId);

        if(currentTime - clientWindowStart >= windowSize){
            windowStartTime.put(clientId, currentTime);
            reqCounts.put(clientId, 0);
        }

        int curCount = reqCounts.get(clientId);
        if(curCount < maxRequests){
            reqCounts.put(clientId, curCount+1);
            return true;
        }

        return false;
    }
}

class SlidingWindowRL implements RateLimiter{

    long windowSize;
    long maxRequests;
    Map<String, Queue<Long>> windowStartTime;


    SlidingWindowRL(long ws, long count){
        windowSize = ws;
        maxRequests = count;
        windowStartTime = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {
        long curTime = System.currentTimeMillis();
        windowStartTime.putIfAbsent(clientId, new LinkedList<>());

        Queue<Long> timestamps = windowStartTime.get(clientId);

        while(!timestamps.isEmpty() && curTime - timestamps.peek() > windowSize){
            timestamps.poll();
        }

        if(timestamps.size() < maxRequests){
            timestamps.add(curTime);
            return true;
        }

        return false;
    }
}

// create these RL obj -->  factory

class RateLimiterFactory{
    static RateLimiter createRateLimiter(String type, long windSize, long count){
        switch (type.toLowerCase()){
            case "fixed":
                return new FixedWindowRL(windSize, count);
            case "sliding":
                return new SlidingWindowRL(windSize, count);
            default:
                throw new IllegalArgumentException("invalid sliding window type.");
        }
    }
}

// create one instance of RL only -- > singleton

class RateLimiterManager{
    private static RateLimiterManager instance;
    private RateLimiter rateLimiter;

    private RateLimiterManager(){
        this.rateLimiter = RateLimiterFactory.createRateLimiter("fixed", 100, 5000);
    }

    static RateLimiterManager getInstance(){
        if(instance == null){
            synchronized (RateLimiterManager.class){
                if(instance == null){
                    instance = new RateLimiterManager();
                }
            }
        }

        return instance;
    }

    boolean allowRequest(String clientId){
        return rateLimiter.allowRequest(clientId);
    }
}


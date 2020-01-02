//package org.fluentness.service.cache;
//
//import org.fluentness.service.logger.Logger;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MemoryCache implements Cache {
//
//    private final Logger logger;
//
//    private final Map<String, String> cache = new HashMap<>();
//
//    public MemoryCache(Logger logger) {
//        this.logger = logger;
//    }
//
//    @Override
//    public String cache(Request request, Object inMissCase) {
//
//        String hash = request.getMethod() + " " + request.getUri();
//        if (cache.containsKey(hash)) {
//            logger.debug("Cache hit for %s", hash);
//            return cache.get(hash);
//        }
//
//        cache.put(hash, (String) inMissCase);
//        logger.debug("Cache miss for %s", hash);
//        return (String) inMissCase;
//    }
//
//}

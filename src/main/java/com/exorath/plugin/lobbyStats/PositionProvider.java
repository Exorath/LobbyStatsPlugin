package com.exorath.plugin.lobbyStats;

import com.exorath.service.stats.api.StatsServiceAPI;
import com.exorath.service.stats.res.GetTopPlayersReq;
import com.exorath.service.stats.res.GetTopPlayersRes;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by toonsev on 6/9/2017.
 */
public class PositionProvider {
    private static final long REFRESH_TIME = TimeUnit.SECONDS.toMillis(30);
    private StatsServiceAPI statsServiceAPI;

    private HashMap<GetTopPlayersReq, Long> lastUpdateMap = new HashMap<>();
    private HashMap<GetTopPlayersReq, GetTopPlayersRes> responses = new HashMap<>();

    public PositionProvider(StatsServiceAPI statsServiceAPI) {
        this.statsServiceAPI = statsServiceAPI;

    }

    public GetTopPlayersRes getTopPlayers(GetTopPlayersReq req) {
        if (shouldUpdate(req)) {
            GetTopPlayersRes res = statsServiceAPI.getTopWeeklyPlayers(req);
            addResponse(req, res);
            return res;
        } else
            return getResponse(req);
    }

    private synchronized boolean shouldUpdate(GetTopPlayersReq req) {
        Long lastUpdate = lastUpdateMap.get(req);
        if (lastUpdate == null || !hasResponse(req))
            return true;
        return lastUpdate + REFRESH_TIME < System.currentTimeMillis();

    }

    private synchronized boolean hasResponse(GetTopPlayersReq req) {
        return responses.containsKey(req);
    }

    private synchronized GetTopPlayersRes getResponse(GetTopPlayersReq req) {
        return responses.get(req);
    }

    private synchronized void addResponse(GetTopPlayersReq req, GetTopPlayersRes res) {
        lastUpdateMap.put(req, System.currentTimeMillis());
        responses.put(req, res);

    }
}

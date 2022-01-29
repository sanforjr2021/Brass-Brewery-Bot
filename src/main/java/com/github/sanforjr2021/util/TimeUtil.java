package com.github.sanforjr2021.util;

import java.time.Instant;

public class TimeUtil {
    public static long getUnixTimeStamp(){
        return Instant.now().getEpochSecond();
    }
    public static long get1MonthAheadUnixTimeStamp(){
        return getUnixTimeStamp() + 2592000;
    }
}

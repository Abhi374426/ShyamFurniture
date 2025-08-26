package com.shyamfurniture.utils;

import java.util.UUID;

public class Utils {

    public static String generatedUUID(int length){
        return UUID.randomUUID().toString().replace("-","").substring(0,length);
    }
}

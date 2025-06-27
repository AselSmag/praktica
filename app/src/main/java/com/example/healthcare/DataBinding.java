package com.example.healthcare;

public class DataBinding {
    private static String BearerToken;
    private static String uuidUser;

    public static String getBearerToken() {
        return BearerToken;
    }

    public static void saveBearerToken(String bearerToken) {
        BearerToken = bearerToken;
    }

    public static String getUuidUser() {
        return uuidUser;
    }

    public static void saveUuidUser(String uuidUser) {
        DataBinding.uuidUser = uuidUser;
    }
}

package com.votacerto.network;

public class Api {
    private static EndpointInterface apiInstance =
            ServiceGenerator.createService(EndpointInterface.class, "http://192.168.1.172:3000/");

    public static EndpointInterface getInstance() {
        return apiInstance;
    }

    private Api() {

    }
}

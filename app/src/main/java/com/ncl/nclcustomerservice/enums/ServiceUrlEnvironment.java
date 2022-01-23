package com.ncl.nclcustomerservice.enums;

/**
 * Created by Venkateswarlu SKP on 04-01-2017.
 */

public enum ServiceUrlEnvironment {



    DEV("DEV ENVIRONMENT",  "http://183.82.4.69:8080/ncldev_test/"),
   // DEV("DEV ENVIRONMENT",  "http://183.82.4.69:8080/ncldev/"),
    //DEV("DEV ENVIRONMENT", /*"http://suprasoftapp.com/ncl/"*/ "http://suprasoftapp.com/ncluat/"),

    TEST("TEST ENVIRONMENT", "http://183.82.122.35:8080/ncldeven/"),

    PRODUCTION("PRODUCTION ENVIRONMENT", "http://183.82.122.35:8080/ncl/");



    private final String environmentType;
    private final String apiUrl;


    ServiceUrlEnvironment(String environmentType, String apiUrl) {

        this.environmentType = environmentType;

        this.apiUrl = apiUrl;

    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public String getApiUrl() {
        return apiUrl;
    }


}

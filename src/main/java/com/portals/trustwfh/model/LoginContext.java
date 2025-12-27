package com.portals.trustwfh.model;

public class LoginContext {

    private final boolean deviceKnown;

    private LoginContext(boolean deviceKnown) {
        this.deviceKnown = deviceKnown;
    }

    public static LoginContext from(LoginRequest request) {
        return new LoginContext(request.isDeviceKnown());
    }

    public boolean isDeviceKnown() {
        return deviceKnown;
    }
}


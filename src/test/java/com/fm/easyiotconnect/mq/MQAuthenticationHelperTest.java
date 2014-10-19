package com.fm.easyiotconnect.mq;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class MQAuthenticationHelperTest {

    @Test
    public void authenticateConnectionTest(){

        String username = "fabio";
        String password = "$2a$10$Tb1i.TBjMy6ZflrNfR.4nu/X9DOj6rEuUMDJwaWGxVgK4GcTBvWaC";

        String connectionUrl = "http://easyiotconnect-fmprdone.rhcloud.com/security/connection";

        Boolean result = false;
        try {
            result = MQAuthenticationHelper.authenticateConnection(username, password, connectionUrl);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Assert.assertTrue(result);
    }
}
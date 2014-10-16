package com.fm.easyiotconnect.mq;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;

/**
 * @author Fabio Marini
 */
public class MQAuthenticationPlugin implements BrokerPlugin {

    private String serverUrlConnectionCheck;

    private String serverUrlSessionCheck;

    public Broker installPlugin(Broker broker) throws Exception
    {
        return new MQAuthenticationBroker(broker, serverUrlConnectionCheck, serverUrlSessionCheck);
    }

    public void setServerUrlConnectionCheck(String serverUrlConnectionCheck) {
        this.serverUrlConnectionCheck = serverUrlConnectionCheck;
    }

    public void setServerUrlSessionCheck(String serverUrlSessionCheck) {
        this.serverUrlSessionCheck = serverUrlSessionCheck;
    }
}

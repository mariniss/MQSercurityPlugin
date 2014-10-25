package com.fm.easyiotconnect.mq;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ProducerInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Fabio Marini
 */
public class MQAuthenticationBroker extends BrokerFilter {

    private static final Log logger = LogFactory.getLog(MQAuthenticationBroker.class);

    private String serverUrlConnectionCheck;

    private String serverUrlSessionCheck;

    /**
     * Constructor
     */
    public MQAuthenticationBroker(Broker next, String serverUrlConnectionCheck, String serverUrlSessionCheck)
    {
        super(next);

        this.serverUrlConnectionCheck = serverUrlConnectionCheck;
        this.serverUrlSessionCheck = serverUrlSessionCheck;

        logger.info("Created MQAuthenticationBroker, serverUrlConnectionCheck: " + serverUrlConnectionCheck +
                                                 " - serverUrlSessionCheck: " + serverUrlSessionCheck);
    }

    @Override
    public void addConnection(ConnectionContext context, ConnectionInfo info) throws Exception
    {
        String userName = info.getUserName();
        String password = info.getPassword();

        if(logger.isDebugEnabled()) {
            logger.debug("Connection request from " + userName);
        }

        if("system".equals(userName))
        {
            super.addConnection(context, info);
            return;
        }
        else if(MQAuthenticationHelper.authenticateConnection(userName, password, serverUrlConnectionCheck))
        {
            super.addConnection(context, info);
            return;
        }

        throw new SecurityException("Connecting from " + userName + " " + password + " not allowed!" );
    }


    public void removeConnection(ConnectionContext context, ConnectionInfo info, Throwable error) throws Exception
    {
        super.removeConnection(context, info, error);
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception
    {
        String username = context.getUserName();
        String destination = info.getDestination().getQualifiedName();

        if(logger.isDebugEnabled()) {
            logger.debug("Consumer request from " + username + " on " + destination);
        }

        if("ActiveMQ.Advisory.TempQueue,ActiveMQ.Advisory.TempTopic".equals(destination))
        {
            return super.addConsumer(context, info);
        }
        else if (MQAuthenticationHelper.authenticateSession(username, destination, serverUrlSessionCheck))
        {
            return super.addConsumer(context, info);
        }
        else
        {
            throw new SecurityException("User " + context.getUserName() + " is not authorized to write ");
        }
    }

    @Override
    public void addProducer(ConnectionContext context, ProducerInfo info) throws Exception
    {
        String username = context.getUserName();
        String destination = info.getDestination().getPhysicalName();

        if(logger.isDebugEnabled()) {
            logger.debug("Producer request from " + username + " on " + destination);
        }

        if (MQAuthenticationHelper.authenticateSession(username, destination, serverUrlSessionCheck))
        {
            super.addProducer(context, info);
        }
        else
        {
            throw new SecurityException("User " + context.getUserName() + " is not authorized to write ");
        }
    }
}

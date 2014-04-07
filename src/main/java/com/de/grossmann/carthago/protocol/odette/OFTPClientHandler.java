package com.de.grossmann.carthago.protocol.odette;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.de.grossmann.carthago.protocol.odette.data.command.Command;
import com.de.grossmann.carthago.protocol.odette.data.command.SSID;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OFTPClientHandler extends ChannelHandlerAdapter
{

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(OFTPClientHandler.class);
    }

    /**
     * Calls {@link io.netty.channel.ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link io.netty.channel.ChannelHandler} in the {@link io.netty.channel.ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        Command command = (Command)msg;

        LOGGER.info("RCV: " + command);

        SSID ssid = new SSID();
        ssid.setSsidlev(5L);
        ssid.setSsidcode("OEVOMIGWIN");
        ssid.setSsidpswd("111111");
        ssid.setSsidsdeb(99999L);
        ssid.setSsidsr("B");
        ssid.setSsidcmpr("N");
        ssid.setSsidrest("N");
        ssid.setSsidspec("N");
        ssid.setSsidcred(999L);
        ssid.setSsidauth("N");
        ssid.setSsidrsv1("");
        ssid.setSsiduser("");
        ssid.setSsidcr("\r");

        ctx.writeAndFlush(ssid);
    }

    //    @Override
//    public void messageReceived(final ChannelHandlerContext channelHandlerContext, final Command command) throws Exception {
//        ActorSystem protocolAdapter = ActorSystem.create("ProtocolAdapter");
//        ActorRef commandSender = protocolAdapter.actorOf(new Props(OFTPStateMachine.class));
//        commandSender.tell(command,commandSender);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("Unexpected exception from downstream. " + cause.getMessage());
        ctx.close();
    }
}

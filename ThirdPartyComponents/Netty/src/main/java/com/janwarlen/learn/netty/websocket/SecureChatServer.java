package com.janwarlen.learn.netty.websocket;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    public ChannelHandler createInitializer(ChannelGroup channelGroup) {
        return new SecureChatServerInitializer(channelGroup, context);
    }

    /**
     * chrome 没有继续访问的选项可以使用其他浏览器，比如 Safari
     */
    public static void main(String[] args) throws Exception {
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        SslContextBuilder sslContextBuilder = SslContextBuilder.forServer(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey());
        SslContext build = sslContextBuilder.build();
        SecureChatServer secureChatServer = new SecureChatServer(build);
        ChannelFuture start = secureChatServer.start(new InetSocketAddress(9998));
        Runtime.getRuntime().addShutdownHook(new Thread(secureChatServer::destory));
        start.channel().closeFuture().syncUninterruptibly();
    }
}

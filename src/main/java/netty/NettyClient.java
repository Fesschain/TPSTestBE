package main.java.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import main.java.netty.model.RequestData;
import main.java.netty.model.TransactionRequest;

import static main.resources.Resources.HOST;
import static main.resources.Resources.PORT;

public class NettyClient {


    private static Bootstrap b = new Bootstrap();
    private static Channel channel;


    public static Channel getNodeChannel() throws InterruptedException {
        if (b!=null && channel!=null && channel.isOpen()) return channel;
        if (b!=null && channel!=null) b.connect(HOST, PORT).sync();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(),
                            new ResponseDataDecoder(), new ClientHandler());
                }
            });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            channel = f.channel();
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
            return null;
        }
//        finally {
//            workerGroup.shutdownGracefully();
//        }
    }

    public static void reopenChannel() throws InterruptedException {
        b.connect(HOST, PORT).sync();
    }
}
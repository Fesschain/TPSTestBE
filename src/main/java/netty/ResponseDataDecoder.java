package main.java.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import main.java.netty.model.ResponseData;

import java.nio.charset.Charset;
import java.util.List;

public class ResponseDataDecoder
        extends ReplayingDecoder<ResponseData> {

    private final Charset charset = Charset.defaultCharset();

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {

        ResponseData data = new ResponseData();
        int strLen = in.readInt();
        data.setHash(in.readCharSequence(strLen, charset).toString());
        out.add(data);
    }
}

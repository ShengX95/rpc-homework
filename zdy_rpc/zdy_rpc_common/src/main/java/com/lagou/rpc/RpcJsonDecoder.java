package com.lagou.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author shengx
 * @date 2020/4/14 19:51
 */
public class RpcJsonDecoder extends ByteToMessageDecoder {
    private Class<?> clazz;
    private Serializer serializer;

    public RpcJsonDecoder(Class<?> clazz, Serializer serializer) {
        this.clazz = clazz;
        this.serializer = serializer;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(clazz != null && byteBuf.readableBytes() >= 4) {
            int length = byteBuf.readInt();
            if(byteBuf.readableBytes() < length)
                return;
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);
            Object o = serializer.deserialize(clazz, bytes);
            list.add(o);
        }
    }
}

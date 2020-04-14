package com.lagou.handler;

import com.lagou.rpc.RpcRequest;
import com.lagou.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class UserServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 判断是否符合约定，符合则调用本地方法，返回数据
        // msg:  UserService#sayHello#are you ok?
        RpcRequest rpcRequest = (RpcRequest) msg;
        System.out.println("receive: " + rpcRequest);
        String result = userService.sayHello(rpcRequest.getParameters()[0].toString());
        ctx.writeAndFlush(result);
    }
}

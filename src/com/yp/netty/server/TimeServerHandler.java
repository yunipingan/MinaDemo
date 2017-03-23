package com.yp.netty.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class TimeServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] b = new byte[buf.readableBytes()];
		buf.readBytes(b);
		System.out.println("client get msg:"+new String(b, "UTF-8"));
		String currentTime = new Date(System.currentTimeMillis()).toString();
		ByteBuf buf2 = Unpooled.buffer(currentTime.getBytes().length);
		buf2.writeBytes(currentTime.getBytes());
		ctx.writeAndFlush(buf2);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server read complete");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("server exceptionCaught");
		ctx.close();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		System.out.println("server close");
	}

	
}

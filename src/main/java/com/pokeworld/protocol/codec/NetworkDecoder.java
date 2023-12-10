package com.pokeworld.protocol.codec;

import com.pokeworld.protocol.ServerMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NetworkDecoder extends FrameDecoder
{

	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
	{
		try
		{
			if(buffer.readableBytes() < 4)
				return null;
			buffer.markReaderIndex();
			long length = buffer.readUnsignedInt();
			if(buffer.readableBytes() < length)
			{
				buffer.resetReaderIndex();
				return null;
			}
			int id = buffer.readUnsignedByte();
			//System.out.println("[ID] <- " + id + " [Length] <- " + length);
			return new ServerMessage(buffer, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
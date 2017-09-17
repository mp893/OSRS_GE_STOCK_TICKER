package channelClient;

import chanelHandler.ItemRequestHandler;
import chanelHandler.PipelineInitializer;
import commons.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ItemRequesterClient {

	public static void main(String[] args){
		
		EventLoopGroup group = new NioEventLoopGroup();
		PipelineInitializer pipeline = new PipelineInitializer();
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				 .channel(NioSocketChannel.class)
				 .handler(pipeline);
		
		Channel channel = null;
		try {
			channel = bootstrap.connect(Constants.host,Constants.port).sync().channel();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

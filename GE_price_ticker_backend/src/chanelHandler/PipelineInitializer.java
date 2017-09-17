package chanelHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class PipelineInitializer extends ChannelInitializer<SocketChannel> {
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // ...
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("RequestReadHandler", new RequestReaderHandler());
    }

}

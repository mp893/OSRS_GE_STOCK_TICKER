package chanelHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class RequestReaderHandler extends SimpleChannelInboundHandler<HttpObject>{
	

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject obj) throws Exception {
		
		HttpResponse response = null;
		if(obj instanceof HttpResponse){
			response = (HttpResponse) obj;
		}
		
		HttpHeaders headers = response.headers();
		Iterator<Entry<String, String>> it = headers.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
	}

}

package se.callista.cadec2017.rxjava;

import io.reactivex.netty.protocol.http.server.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.RxNettyHttpHandlerAdapter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.DelegatingWebReactiveConfiguration;

import java.net.InetSocketAddress;

@SpringBootApplication
public class BookStoreSpringReactiveApplication {

//	@Bean
//	public HttpServer getHttpServer(){
//		ApplicationContext context = new AnnotationConfigApplicationContext(DelegatingWebReactiveConfiguration.class);
//
//		HttpHandler handler = DispatcherHandler.toHttpHandler(context);

//		RxNettyHttpHandlerAdapter adapter = new RxNettyHttpHandlerAdapter(handler);

//		HttpServer server = HttpServer.newServer(new InetSocketAddress("localhost", 8060));
//		server.start(adapter);
//		server.awaitShutdown();
//		return server;
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreSpringReactiveApplication.class, args);
	}
}

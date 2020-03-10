package jp.co.example.ecommerce_c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * クレジットカード有効性確認用.
 * 
 * @author hatakeyamakouta
 *
 */
@SpringBootApplication
public class SampleWebApiClientApplication {

	/**
	 * クレジットカード有効性確認用.
	 * 
	 * @param args args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SampleWebApiClientApplication.class, args);
	}
	
	/**
	 * WebAPI通信をする際に使用するオブジェクトをDIコンテナに登録します.
	 * 
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate setUpRestTemplate() {
		return new RestTemplate();
	}

}

package jp.co.example.ecommerce_c.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.example.ecommerce_c.domain.RequestCreditCardPaymentApiDomain;
import jp.co.example.ecommerce_c.domain.ResponseCreditCardPaymentApiDomain;
import jp.co.example.ecommerce_c.form.OrderForm;

/**
 * カード決済APIを呼び出すサービスクラス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
public class CreditCardPaymentApiCallService {

	@Autowired
	private RestTemplate restTemplate;

	// 外部サーバで動いているWEB-APIのURL
	private static final String URL = "http://153.126.176.141:8080/sample-credit-card-web-api/credit-card/payment"; 

	/**
	 * カード決済WebAPIを呼び出し、レスポンスを返す.
	 * 
	 * @param form クレジットカード情報
	 * @return　WebAPIのレスポンスが入ったドメイン
	 */
	public ResponseCreditCardPaymentApiDomain paymentApiService(OrderForm form) {
		RequestCreditCardPaymentApiDomain requestCreditCardPaymentApiDomain = new RequestCreditCardPaymentApiDomain();
		BeanUtils.copyProperties(form, requestCreditCardPaymentApiDomain);
		requestCreditCardPaymentApiDomain.setUser_id(0);//クレジットカードの有効性を確認するために仮で値をセット
		requestCreditCardPaymentApiDomain.setOeder_number("0");//クレジットカードの有効性を確認するために仮で値をセット
		requestCreditCardPaymentApiDomain.setAmount("0");//クレジットカードの有効性を確認するために仮で値をセット
		return restTemplate.postForObject(URL, form, ResponseCreditCardPaymentApiDomain.class);
	}
}

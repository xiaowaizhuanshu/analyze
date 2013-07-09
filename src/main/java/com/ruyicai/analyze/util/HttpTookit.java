package com.ruyicai.analyze.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruyicai.analyze.domain.Torder;

import flexjson.JSONDeserializer;

/**
 * HTTP工具箱
 * 
 * 
 */
public final class HttpTookit {

	private static Logger logger = LoggerFactory.getLogger(HttpTookit.class);

	 private static final String LOTTERY =
	 ResourceBundle.getBundle("url").getString("url.lottery");
	 private static final String FINDTORDER =
	 ResourceBundle.getBundle("url").getString("url.findtorder");
//	private static final String LOTTERY = "http://192.168.0.42:8080/lottery";
//	private static final String FINDTORDER = "/select/getTorder";

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	public static String doGet(String url, String queryString) {
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (StringUtils.isNotBlank(queryString))
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (URIException e) {
			logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response;
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	public static String doPost(String url, Map<String, String> params) {
		String response = null;
		HttpClient client = new HttpClient();
		HttpMethod method = new PostMethod(url);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 6000);

		// 设置Http Post数据
		if (!params.isEmpty()) {
			NameValuePair[] data = new NameValuePair[params.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : params.entrySet()) {
				data[i] = new NameValuePair(entry.getKey(),entry.getValue());
				i = i + 1;
			}
			method.setQueryString(data);
		}

		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (IOException e) {
			logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();  
		}

		return response;
	}

	/**
	 * 查询order详细信息
	 * 
	 * @param orderid
	 * @return
	 */
	public static Torder findtorder(String orderid) {

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("orderid", orderid);

			logger.info("notice doPost:" + LOTTERY + FINDTORDER);
			String rep = doPost(LOTTERY + FINDTORDER, params);
			logger.info("findorder 返回" + rep);

			JSONDeserializer<HashMap<String, Object>> json = new JSONDeserializer<HashMap<String, Object>>();
			Map<String, Object> response = json.deserialize(rep);
			if (response == null
					|| !"0".equals((String) response.get("errorCode"))) {
				logger.info("查询订单详细失败orderid" + orderid);
				throw new RuntimeException("查询订单详细失败orderid" + orderid);
			}

			@SuppressWarnings("unchecked")
			Map<String, Object> order = (Map<String, Object>) response
					.get("value");

			Torder torder = new Torder();
			if (order.get("batchcode") == null) {
				torder.setBatchcode(null);
			} else {
				torder.setBatchcode(order.get("batchcode").toString());
			}
			torder.setLotmulti(new BigDecimal(order.get("lotmulti").toString()));
			torder.setLotno(order.get("lotno").toString());
			torder.setOrderInfo(order.get("orderinfo").toString());
			torder.setPlaytype(order.get("playtype").toString());
			torder.setUserno(order.get("userno").toString());
			if (order.get("orderprizeamt") == null
					|| order.get("orderprizeamt").toString().equals("")) {
				torder.setPrizeamt(BigDecimal.ZERO);
			} else {
				torder.setPrizeamt(new BigDecimal(order.get("orderprizeamt")
						.toString()));
			}
			return torder;

		} catch (Exception e) {
			logger.info("查询订单详细失败orderid" + orderid);
			throw new RuntimeException("查询订单详细失败orderid" + orderid);
		}
	}

	public static void main(String[] args) {
		Torder t = findtorder("TE2012041100065032");
		System.out.println(t);

	}

}

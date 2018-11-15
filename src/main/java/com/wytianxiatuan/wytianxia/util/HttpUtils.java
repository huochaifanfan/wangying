package com.wytianxiatuan.wytianxia.util;

import android.content.Context;
import android.os.Build;

import com.wytianxiatuan.wytianxia.application.MainApplication;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


/**
 * http请求工具类
 * @author Administrator
 *
 */
public class HttpUtils {
	/** Https 证书验证对象 */
	private static SSLContext sslContext;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Callback.Cancelable send(HttpMethod method,Context context,RequestParams params, Callback.CommonCallback callBack){
		params.setConnectTimeout(20*1000);
//		params.addHeader("User-Agent",Constants.userAgent);
		params.addHeader("MYAPP",Constants.userAgent);

		/**判断https证书是否成功验证 */
//		SSLContext sslContext = getSSLContext(context);
//		if (sslContext == null) {
//			Toast.makeText(context , "证书验证失败" , Toast.LENGTH_SHORT).show();
//			return;
//		}
//		params.setSslSocketFactory(sslContext.getSocketFactory());
		Callback.Cancelable cancelable = x.http().request(method,params, callBack);
		return cancelable;
	}
	private static SSLContext getSSLContext(Context context) {
		if (sslContext != null) {
			return sslContext;
		}
		CertificateFactory certificateFactory;//解析和管理证书
		InputStream inputStream;
		KeyStore keystore;
		String tmfAlgorithm;
		TrustManagerFactory trustManagerFactory;//基于信任材料源的信任管理器的工厂
		try {
			certificateFactory = CertificateFactory.getInstance("X.509");//请求的信任管理算法的标准名称
			inputStream = context.getAssets().open("aimutou.pem");//这里导入SSL证书文件
			Certificate ca = certificateFactory.generateCertificate(inputStream);
			keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(null, null);
			keystore.setCertificateEntry("ca", ca);
			tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();//获取默认的 TrustManagerFactory 算法名称。
			trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
			trustManagerFactory.init(keystore);
			sslContext = SSLContext.getInstance("TLS");//返回实现指定安全套接字协议的 SSLContext 对象
			sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
			return sslContext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

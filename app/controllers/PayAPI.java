package controllers;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import models.YeWuBanLi;

import com.jamonapi.utils.Logger;

import play.mvc.Controller;
import utils.pay.config.AlipayConfig;
import utils.pay.util.AlipayNotify;
import utils.pay.util.AlipaySubmit;

public class PayAPI extends Controller{
	
	
	public static	java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
	
	
	public static void alipay(String fee,String trade_no){
		
		
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://www.yongdiantong.com/notify_url";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		
		String return_url = "http://www.yongdiantong.com/returnurl";
		//String return_url = "http://127.0.0.1:9999/returnurl";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//卖家支付宝帐户
		String seller_email = "ghypower@163.com";
		//必填

		//商户订单号
		String out_trade_no = trade_no;
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		String subject = "代缴电费";
		//必填

		//付款金额
		String total_fee = fee;
		//必填

		//订单描述

		String body = "代缴电费";
		//商品展示地址
		String show_url = "";
		//需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数

		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		renderHtml(sHtmlText);
		
	}
	
	
	public  static void bankPay(String bankcode){
		
		//支付类型
				String payment_type = "1";
				//必填，不能修改
				//服务器异步通知页面路径
				String notify_url = "http://www.yongdiantong.com/notify_url";
				//需http://格式的完整路径，不能加?id=123这类自定义参数

				//页面跳转同步通知页面路径
				String return_url = "http://www.yongdiantong.com/returnurl";
				//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

				//卖家支付宝帐户
				String seller_email = "ghypower@163.com";
				//必填

				//商户订单号
				String out_trade_no =getFixLenthString(8);
				//商户网站订单系统中唯一订单号，必填

				//订单名称
				String subject = "测试代码";
				//必填

				//付款金额
				String total_fee = "0.02";
				//必填

				//订单描述

				String body = "test";
				//默认支付方式
				String paymethod = "bankPay";
				//必填
				//默认网银
				String defaultbank = bankcode;
				//必填，银行简码请参考接口技术文档

				//商品展示地址
				String show_url = "";
				//需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html

				//防钓鱼时间戳
				String anti_phishing_key = "";
				//若要使用请调用类文件submit中的query_timestamp函数

				//客户端的IP地址
				String exter_invoke_ip = "";
				//非局域网的外网IP地址，如：221.0.0.1
				
				
				//////////////////////////////////////////////////////////////////////////////////
				
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "create_direct_pay_by_user");
		        sParaTemp.put("partner", AlipayConfig.partner);
		        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("payment_type", payment_type);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("return_url", return_url);
				sParaTemp.put("seller_email", seller_email);
				sParaTemp.put("out_trade_no", out_trade_no);
				sParaTemp.put("subject", subject);
				sParaTemp.put("total_fee", total_fee);
				sParaTemp.put("body", body);
				sParaTemp.put("paymethod", paymethod);
				sParaTemp.put("defaultbank", defaultbank);
				sParaTemp.put("show_url", show_url);
				sParaTemp.put("anti_phishing_key", anti_phishing_key);
				sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
				
				//建立请求
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
				renderHtml(sHtmlText);
		
		
		
	}
	
	
	
	
	public static void returnurl(String out_trade_no,String trade_no,String trade_status ) throws UnsupportedEncodingException{
		Logger.logInfo("###################    start  returnurl  " );
		//获取支付宝GET过来反馈信息
		Map<String,String> params_tp = new HashMap<String,String>();
		
		Map requestParams = (Map) params.data;
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params_tp.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

	

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params_tp);
		
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			
			
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				verifyOK(out_trade_no,"returnurl");
				
			}
			
			
			Logger.logInfo("###################  laishi " );
			//该页面可做页面美工编辑
			renderHtml("验证成功<br />");
			
			
			Logger.logInfo("###################   验证成功 wanbi " );
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			//该页面可做页面美工编辑
			
			renderHtml("验证失败<br />");
		}
		
		
	}
	
	
	public static void notify_url(String out_trade_no,String trade_no,String trade_status) throws UnsupportedEncodingException{
		
		Logger.logInfo("###################    start  notify_url  " );
		
		//获取支付宝GET过来反馈信息
		Map<String,String> params_tp = new HashMap<String,String>();
		
		Map requestParams = (Map) params.data;
		
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params_tp.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

	

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params_tp);
		
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			
			
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				verifyOK(out_trade_no,"notify_url");
				
			}
			
			renderHtml("success");
			
				//请不要修改或删除
			
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{
			
			
			renderHtml("fail");
		}
		
		
	
		
	}
	
	
	public static void  verifyOK(String out_trade_no,String from){
		
		String s = format.format(new Date());
		YeWuBanLi yewu = YeWuBanLi.find("trade_no= ?", out_trade_no).first();
		yewu.orderstate="3";
		Logger.logInfo("###################     this is from :   "+from );
		if(yewu.save().isPersistent())
			Logger.logInfo("###################     ok    time:"+s+",单号:"+out_trade_no+",user:"+session.get("user") );
		
		else
			Logger.logInfo("###################    error  time:"+s+",单号:"+out_trade_no+",user:"+session.get("user")+"dataid:"+yewu.id );
		
		
	}
	
	
	
	
	/* 
	 * 返回长度为【strLength】的随机数，在前面补0 
	 */  
	private static String getFixLenthString(int strLength) {  
	      
	    Random rm = new Random();  
	      
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}  

}

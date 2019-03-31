package com.nbd.ocp.core.repository.response;
/*
                       _ooOoo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      O\  =  /O
                   ____/`---'\____
                 .'  \\|     |//  `.
                /  \\|||  :  |||//  \
               /  _||||| -:- |||||-  \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |   |
               \  .-\__  `-`  ___/-. /
             ___`. .'  /--.--\  `. . __
          ."" '<  `.___\_<|>_/___.'  >'"".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `-.   \_ __\ /__ _/   .-` /  /
    ======`-.____`-.___\_____/___.-`____.-'======
                       `=---='
    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
             佛祖保佑       永无BUG
*/





import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author jin
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OcpJsonResponse<T> extends LinkedHashMap<String, Object> implements Serializable{
	private static final long serialVersionUID = -3957696416833580484L;

	private static final String STATUS = "status";

	private static final String CODE = "code";


	private static final String DATA = "data";


	private static final String MSG = "msg";

	private static final String STATUS_CODE = "statusCode";

	public OcpJsonResponse() {
	}


	public static OcpJsonResponse failed() {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.FAILED_STATUS);
		ocpJsonResponse.setCode(OcpJsonContract.FAILED_CODE);
		ocpJsonResponse.setMsg("执行失败");
		return ocpJsonResponse;
	}
	public static OcpJsonResponse failed(String message) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.FAILED_STATUS);
		ocpJsonResponse.setCode(OcpJsonContract.FAILED_CODE);
		ocpJsonResponse.setMsg(message);
		return ocpJsonResponse;
	}
	public static OcpJsonResponse failed(String code, String msg) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.FAILED_STATUS);
		ocpJsonResponse.setCode(code);
		ocpJsonResponse.setMsg(msg);
		return ocpJsonResponse;
	}

	public static OcpJsonResponse failed(String code, int statusCode, String msg) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.FAILED_STATUS);
		ocpJsonResponse.setCode(code);
		ocpJsonResponse.setMsg(msg);
		ocpJsonResponse.setStatusCode(statusCode);
		return ocpJsonResponse;
	}

	public static OcpJsonResponse success() {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.SUCCESS_STATUS);
		ocpJsonResponse.setMsg("成功");
		return ocpJsonResponse;
	}

	public static OcpJsonResponse success(String msg) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.SUCCESS_STATUS);
		ocpJsonResponse.setMsg(msg);
		return ocpJsonResponse;
	}
	public static OcpJsonResponse success(Object data) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.SUCCESS_STATUS);
		ocpJsonResponse.setMsg("成功");
		ocpJsonResponse.setData(data);
		return ocpJsonResponse;
	}
	public static OcpJsonResponse success(String msg, Object data) {
		OcpJsonResponse ocpJsonResponse =new OcpJsonResponse();
		ocpJsonResponse.setStatus(OcpJsonContract.SUCCESS_STATUS);
		ocpJsonResponse.setMsg(msg);
		ocpJsonResponse.setData(data);
		return ocpJsonResponse;
	}




	public int getStatus() {
		Object status=get(STATUS);
		return Integer.valueOf(status==null?"0":status.toString());
	}

	public void setStatus(int status) {
		put(STATUS,status);
	}

	public String getMsg() {
		Object msg=get(MSG);
		return msg==null?null:msg.toString();
	}

	public void setMsg(String msg) {
		put(MSG,msg);
	}

	public T getData() {
		Object data=get(DATA);
		if(data==null){
			return null;
		}else{
			return (T) data;
		}
	}

	public void setData(T data) {
		put(DATA,data);
	}

	public String getCode() {
		Object code=get(CODE);
		return code==null?null:code.toString();
	}
	public void setCode(String code) {
		put(CODE,code);
	}

	public int getStatusCode(){
		Object statusCode=get(STATUS_CODE);
		return statusCode==null?0:Integer.valueOf(statusCode.toString());
	}
	public void setStatusCode(int statusCode) {
		put(STATUS_CODE,statusCode);

	}

	public boolean isSuccess(){
		return OcpJsonContract.SUCCESS_STATUS==getStatus();
	}
}

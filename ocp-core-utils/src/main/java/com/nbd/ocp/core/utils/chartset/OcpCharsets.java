package com.nbd.ocp.core.utils.chartset;

/**
 * @author jhb
 */

public enum OcpCharsets {
	ASCII("ASCII"),
	// 繁体中文
	BIG5("BIG5:"), ISO8859_1("ISO8859-1"), ISO8859_2("ISO8859-2"), ISO_8859_15("ISO-8859-15"), ISO10646_UCS_2(
			"ISO-10646-UCS-2"), GBK("GBK"), GB2312("GB2312"), GB18030(
					"GB18030 "), UTF_8("UTF-8"), UTF_16("UTF-16"), UTF_32("UTF-32"), US_ASCII("US-ASCII"),;
	private String charset;

	OcpCharsets(String charset) {
		this.charset = charset;
	}

	/**
	 * 获取编码格式
	 * 
	 * @return
	 */
	public String getCharset() {
		return charset;
	}

}
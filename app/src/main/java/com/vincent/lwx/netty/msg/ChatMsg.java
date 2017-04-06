package com.vincent.lwx.netty.msg;

/**   
* @Title: ChatMsg.java 
* @Package com.vincent.lwx.netty.msg 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Vincent   
* @date 2017年3月16日 下午11:43:42 
* @version V1.0   
*/
public class ChatMsg extends BaseMsg{

	/**
	 * 表示对方的手机号码
	 */
	private String ask_phone;

	/**
	 * 聊天的内容
	 */
	private String chatContent;

	/**
	 * 类型，0接收到的消息 1表示我发送出去的消息
	 */
	private String msgType;

	/**
	 * 发送状态，主要是服务器做处理，这里只是为了解析
	 */
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAsk_phone() {
		return ask_phone;
	}

	public void setAsk_phone(String ask_phone) {
		this.ask_phone = ask_phone;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}



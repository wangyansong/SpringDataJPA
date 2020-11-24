package com.springdatajpa.base.exception;


public class RRException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;

    public RRException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public RRException(Throwable e) {
		super(e);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

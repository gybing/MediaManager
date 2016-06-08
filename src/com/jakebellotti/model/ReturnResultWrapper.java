package com.jakebellotti.model;

import java.util.Optional;

/**
 * 
 * @author Jake Bellotti
 * @since 7 Jun 2016
 *
 * @param <R> The status to return
 * @param <T> The type to return.
 */
public class ReturnResultWrapper<S extends ReturnStatus, T> {
	
	private final S status;
	private T returnResult;
	
	public ReturnResultWrapper(S status) {
		this.status = status;
	}
	
	public Optional<T> getReturnResult() {
		return Optional.ofNullable(returnResult);
	}
	
	public S getStatus() {
		return status;
	}

	public ReturnResultWrapper<S, T> setReturnResult(T returnResult) {
		this.returnResult = returnResult;
		return this;
	}

}

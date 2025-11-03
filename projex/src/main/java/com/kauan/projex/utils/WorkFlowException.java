package com.kauan.projex.utils;

public class WorkFlowException extends RuntimeException {
    public WorkFlowException(String message) { super(message); }
    public WorkFlowException(String message, Throwable cause) { super(message, cause); }
}

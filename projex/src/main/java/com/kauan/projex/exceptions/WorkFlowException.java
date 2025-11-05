package com.kauan.projex.exceptions;

public class WorkFlowException extends RuntimeException {
    public WorkFlowException(String message) { super(message); }
    public WorkFlowException(String message, Throwable cause) { super(message, cause); }
}

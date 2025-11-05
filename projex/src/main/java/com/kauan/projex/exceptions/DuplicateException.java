package com.kauan.projex.exceptions;

public class DuplicateException extends RuntimeException{
    public  DuplicateException(String message, Object obj){
        super(message + obj );
    }
}

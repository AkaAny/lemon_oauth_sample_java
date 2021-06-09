package com.hduhelp.apidemo.common.response;

import com.hduhelp.apidemo.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseFactory {
    private static final ResponseFactory sInstance=new ResponseFactory();

    public static ResponseFactory getInstance(){
        return sInstance;
    }

    static{
        ResponseFactory factory=getInstance();
        factory.put(new ResponseCreator<IllegalAccessException>() {
            @Override
            public Class<IllegalAccessException> getAppliedClass() {
                return IllegalAccessException.class;
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.FORBIDDEN;
            }

            @Override
            public BaseResponse<?> create(IllegalAccessException e) {
                return BaseResponse.create(ErrorCode.ERROR_FORBIDDEN,e.getMessage());
            }
        });
        factory.put(new ResponseCreator<IllegalArgumentException>() {
            @Override
            public Class<IllegalArgumentException> getAppliedClass() {
                return IllegalArgumentException.class;
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public BaseResponse<?> create(IllegalArgumentException e) {
                return BaseResponse.create(ErrorCode.ERROR_BAD_REQUEST,e.getMessage());
            }
        });
        //主要处理DataIntegrityViolationException
        factory.put(new ResponseCreator<RuntimeException>() {
            @Override
            public Class<RuntimeException> getAppliedClass() {
                return RuntimeException.class;
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public BaseResponse<?> create(RuntimeException e) {
                return BaseResponse.create(ErrorCode.ERROR_INTERNAL_UNKNOWN,e.getMessage());
            }
        });
        //未定义行为在proceed方法中会被包装为UndeclaredThrowableException
        factory.put(new ResponseCreator<UndeclaredThrowableException>() {
            @Override
            public Class<UndeclaredThrowableException> getAppliedClass() {
                return UndeclaredThrowableException.class;
            }

            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public BaseResponse<?> create(UndeclaredThrowableException e) {
                return BaseResponse.create(ErrorCode.ERROR_INTERNAL_UNKNOWN,e.getMessage());
            }
        });
    }

    private final Map<Class<?>,ResponseCreator<?>> mTypeCreatorMap=new ConcurrentHashMap<>();

    public <T> void put(@NonNull ResponseCreator<T> creator){
        mTypeCreatorMap.put(creator.getAppliedClass(),creator);
    }

    @SuppressWarnings("rawtypes")
    public <T> ResponseEntity<BaseResponse<T>> create(Object obj) throws IllegalArgumentException{
        Class<?> objClass=obj.getClass();
        ResponseCreator creator= mTypeCreatorMap.get(objClass);
        if(creator==null){
            throw new IllegalArgumentException("creator for class:"+objClass+" does not exist");
        }
        BaseResponse<T> body= creator.create(obj);
        return new ResponseEntity<>(body,creator.getStatusCode());
    }
}

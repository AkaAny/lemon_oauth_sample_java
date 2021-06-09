package com.hduhelp.apidemo.common.compact;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;

public class FromGoResponse {
    static{
        GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(StubData.class,new Adapter());
        gson=builder.create();
    }

    private static final Gson gson;

    public boolean cache;
    public Integer error;
    public String msg;

    @SerializedName("data")
    private StubData stubData;

    private static class StubData{
        private JsonToken type;
        private Object value;
    }

    public boolean isSuccess(){
        if(error==null){
            return false;
        }
        return error==0;
    }

    public <T extends FromGoResponse> T getData(Class<T> classOfT) {
        if("".equals(stubData.value)){ //go的默认值问题，不设定msg的情况下返回的是""而不是null
            try {
                return classOfT.newInstance();
            }catch (Exception e){ //ignored
                e.printStackTrace();
                return null;
            }
        }
        T result= gson.fromJson(gson.toJson(stubData.value), classOfT);
        result.cache=cache;
        result.error=error;
        result.msg=msg;
        return result;
    }

    public static FromGoResponse create(int error, String msg){
        FromGoResponse response=new FromGoResponse();
        response.error=error;
        response.msg=msg;
        return response;
    }

    public static FromGoResponse fromJson(String json){
        return gson.fromJson(json, FromGoResponse.class);
    }

    private static final class Adapter extends TypeAdapter<StubData>{

        @Override
        public void write(JsonWriter out, StubData stub) throws IOException {
           out.jsonValue(gson.toJson(stub.value));
        }

        @Override
        public StubData read(JsonReader in) throws IOException {
            StubData stub=new StubData();
            JsonToken type=in.peek();
            if (type == JsonToken.STRING) {
                stub.value = in.nextString();
            } else {
                stub.value = gson.fromJson(in, HashMap.class);
            }
            stub.type=type;
            return stub;
        }
    }
}

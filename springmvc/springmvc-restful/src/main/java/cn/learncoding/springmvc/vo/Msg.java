package cn.learncoding.springmvc.vo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Msg<T> {
	
	public static ResponseEntity<?> ok(){
		return new ResponseEntity<>(Msg.data(null), HttpStatus.OK);
	}
	
	public static <T> ResponseEntity<?> ok(T data){
		return new ResponseEntity<>(Msg.data(data), HttpStatus.OK);
	}
	
    public static <T> ResponseEntity<?> created(T data) {
        return new ResponseEntity<>(Msg.data(data), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<?> created() {
    	return new ResponseEntity<>(Msg.data(null), HttpStatus.CREATED);
    }
	
    public static ResponseEntity<?> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	public static ResponseEntity<?> notFind(){
		return new ResponseEntity<>(Msg.msg(""), HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<?> notFind(String msg){
		return new ResponseEntity<>(Msg.msg(msg), HttpStatus.NOT_FOUND);
	}
	
	private static <T> Msg<T> data(T data){
		return new Msg<T>("", data);
	}
	
	private static Msg<?> msg(String msg){
		return new Msg<>(msg, null);
	}

	public Msg() {
	}

	private Msg(String msg, T data) {
		super();
		this.msg = msg;
		this.data = data;
	}

	private String msg;
	private T data;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}

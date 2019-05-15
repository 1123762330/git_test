package com.xnpool.account.cotroller;




import com.xnpool.account.service.exception.*;
import com.xnpool.account.util.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class BaskController {
	private Logger log = LoggerFactory.getLogger(BaskController.class);
	public static final Integer SUCCESS = 200;
	@ResponseBody
	@ExceptionHandler(value = ServiceException.class)
	public ResponseResult<Void> handleException(Exception e) {
		Integer state = null;

		if(e instanceof DataNotFoundException) {
			log.error(e.getClass()+" "+e.getMessage());
			state = 404;
		}else if(e instanceof NotActiveException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 403;
		}else if(e instanceof DataExistException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 417;
		} else if(e instanceof UpdateException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 600;
		}else if(e instanceof InsertException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 601;
		}else if(e instanceof  DeleteException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 602;
		}

		return new ResponseResult<>(state,e);

	}
	protected Integer getUidfromSession(HttpSession session) {
		return  Integer.valueOf(session.getAttribute("userId").toString());
	}
}

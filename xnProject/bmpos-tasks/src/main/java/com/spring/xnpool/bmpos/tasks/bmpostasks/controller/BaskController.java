package com.spring.xnpool.bmpos.tasks.bmpostasks.controller;


import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.ResponseResult;
import com.sun.xml.internal.ws.util.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.NullPointerException;

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
		}else  if(e instanceof ActiveException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 401;
		}else  if (e instanceof NotSupportException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 415;
		}else  if(e instanceof NullPointerException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 416;
		}else if(e instanceof DataExistException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 417;
		} else if(e instanceof SystemException){
			log.error(e.getClass()+" "+e.getMessage());
            state = 500;
		} else if(e instanceof UtilException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 501;
		}else if(e instanceof NullDataException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 502;
		}else if(e instanceof UpdateException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 600;
		}else if(e instanceof InsertException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 601;
		}else if(e instanceof  DeleteException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 602;
		} else if(e instanceof PayOutException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 701;
		}else if(e instanceof ConnectException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 702;
		}else if(e instanceof ErrorMessageException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 703;
		}else if(e instanceof SendmanyException){
			log.error(e.getClass()+" "+e.getMessage());
			state = 704;
		}

		return new ResponseResult<>(state,e);
	}
}

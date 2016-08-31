package me.wonwoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by wonwoo on 2016. 8. 31..
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

  private final String message;

  public NotFoundException(String message){
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}

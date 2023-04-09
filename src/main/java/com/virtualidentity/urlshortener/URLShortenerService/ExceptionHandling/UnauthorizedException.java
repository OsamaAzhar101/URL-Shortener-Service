package com.virtualidentity.urlshortener.URLShortenerService.ExceptionHandling;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }

}

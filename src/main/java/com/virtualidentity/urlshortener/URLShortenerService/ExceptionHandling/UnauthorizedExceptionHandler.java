package com.virtualidentity.urlshortener.URLShortenerService.ExceptionHandling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnauthorizedExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public String handleUnauthorizedException(UnauthorizedException ex, Model model) {
    model.addAttribute("error", "User not authenticated");
    return "error";
  }
}


package com.virtualidentity.urlshortener.URLShortenerService.Util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class URLgenerator {

  public String generateShortUrl(String originalUrl) {
    String allowedCharacters = "sabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int length = 6;
    StringBuilder shortUrl = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(allowedCharacters.length());
      shortUrl.append(allowedCharacters.charAt(index));
    }

    return shortUrl.toString();
  }

}

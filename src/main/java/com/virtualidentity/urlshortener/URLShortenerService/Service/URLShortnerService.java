package com.virtualidentity.urlshortener.URLShortenerService.Service;

import com.virtualidentity.urlshortener.URLShortenerService.ExceptionHandling.UnauthorizedException;
import com.virtualidentity.urlshortener.URLShortenerService.Repository.UrlRepository;
import com.virtualidentity.urlshortener.URLShortenerService.Util.URLgenerator;
import com.virtualidentity.urlshortener.URLShortenerService.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class URLShortnerService {


  @Autowired
  UrlRepository urlRepository;

  @Autowired
  URLgenerator urLgenerator;

  public Url shortenUrl(String originalUrl) {

    if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
      originalUrl = "https://" + originalUrl;
    }
    Url existingUrl = urlRepository.findByOriginalUrl(originalUrl);

    if (existingUrl != null && existingUrl.getOriginalUrl() != null) {
      existingUrl.setShortenedCount(existingUrl.getShortenedCount() + 1);
    } else {
      existingUrl = new Url();
      existingUrl.setOriginalUrl(originalUrl);
      existingUrl.setShortUrl(urLgenerator.generateShortUrl(originalUrl));
      existingUrl.setShortenedCount(1);
      existingUrl.setAccessCount(0);
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      existingUrl.setUsername(authentication.getName());
    }

    return saveURL(existingUrl);
  }

  public List<Url> getUrlsForAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      return findByUserName(username);
    } else {
      throw new UnauthorizedException("User not authenticated");
    }
  }


  public Url redirectUrl(String shortUrl) {
    Url url = urlRepository.findByShortUrl(shortUrl);
    if (url != null) {
      url.setAccessCount(url.getAccessCount() + 1);
      return saveURL(url);
    } else {
      return null;
    }
  }

  public Url saveURL(Url url) {
    return urlRepository.save(url);
  }

  public Url findByShortURL(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }

  public Url findByOriginalUrl(String shortUrl) {

    return urlRepository.findByOriginalUrl(shortUrl);
  }


  public List<Url> findByUserName(String username) {
    return urlRepository.findByUsername(username);
  }

  public List<Url> findAll() {
    return urlRepository.findAll();
  }
}

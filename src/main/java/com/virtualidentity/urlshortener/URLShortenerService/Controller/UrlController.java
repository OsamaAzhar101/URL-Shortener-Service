package com.virtualidentity.urlshortener.URLShortenerService.Controller;

import com.virtualidentity.urlshortener.URLShortenerService.Repository.UrlRepository;
import com.virtualidentity.urlshortener.URLShortenerService.Service.URLShortnerService;
import com.virtualidentity.urlshortener.URLShortenerService.model.Url;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
class UrlController {


  @Autowired
  private URLShortnerService shortnerService;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @PostMapping("/shorten")
  public String shorten(@RequestParam("url") String originalUrl, Model model) {

    Url url = shortnerService.shortenUrl(originalUrl);
    model.addAttribute("url", "http://localhost:8080/" + url.getShortUrl());
    return "result";
  }

  @GetMapping("/{shortUrl}")
  public String redirect(@PathVariable("shortUrl") String shortUrl) {
    Url url = shortnerService.redirectUrl(shortUrl);
    if (url != null) {
      return "redirect:" + url.getOriginalUrl();
    } else {
      return "error";
    }
  }
//  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/stats")
  public String stats(Model model) {
    List<Url> urls = shortnerService.getUrlsForAuthenticatedUser();
    model.addAttribute("urls", urls);
    return "stats";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin/stats")
  public String adminStats(Model model) {
    List<Url> urls = shortnerService.findAll();
    model.addAttribute("urls", urls);
    return "admin_stats";
  }

}

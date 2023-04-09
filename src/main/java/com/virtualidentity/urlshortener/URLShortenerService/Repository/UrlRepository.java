package com.virtualidentity.urlshortener.URLShortenerService.Repository;

import com.virtualidentity.urlshortener.URLShortenerService.model.Url;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
  List<Url> findByUsername(String username);
  Url findByShortUrl(String shortUrl);

  Url findByOriginalUrl(String originalUrl);
}

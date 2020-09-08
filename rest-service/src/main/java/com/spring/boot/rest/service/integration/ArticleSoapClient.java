package com.spring.boot.rest.service.integration;

import com.samples.soap.Article;
import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@AllArgsConstructor
public class ArticleSoapClient {
    private final WebServiceTemplate webServiceTemplate;

    public Article getArticle(String name) {
        GetArticleRequest request = new GetArticleRequest();
        request.setName(name);
        GetArticleResponse response = (GetArticleResponse) webServiceTemplate.marshalSendAndReceive(request);
        return response.getArticle();
    }
}

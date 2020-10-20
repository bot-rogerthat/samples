package com.spring.boot.rest.service.integration.soap.artice.get;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.Article;
import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.rest.service.integration.soap.artice.add.SoapAddArticleService;
import com.spring.boot.rest.service.redelivery.Callback;
import com.spring.boot.rest.service.redelivery.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SoapGetArticleCallback implements Callback<GetArticleRequest, GetArticleResponse> {

    @Autowired
    private SoapAddArticleService soapAddArticleService;

    @Override
    public void onSuccess(GetArticleResponse response, Context<GetArticleRequest> context) {
        Article article = response.getArticle();
        String newName = article.getName() + "new";
        AddArticleRequest addArticleRequest = new AddArticleRequest();
        addArticleRequest.setName(newName);
        addArticleRequest.setDescription("add new");
//        soapAddArticleService.send(new Context<>(context, this.getClass().getSimpleName(), addArticleRequest));
    }

    @Override
    public void onFail(Context<GetArticleRequest> context) {

    }
}

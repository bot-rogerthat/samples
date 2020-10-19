package com.spring.boot.rest.service.integration.soap.artice.get;

import com.samples.soap.AddArticleRequest;
import com.samples.soap.Article;
import com.samples.soap.GetArticleRequest;
import com.samples.soap.GetArticleResponse;
import com.spring.boot.rest.service.integration.soap.artice.add.SoapAddArticleService;
import com.spring.boot.rest.service.redelivery.DefaultAsyncService;
import com.spring.boot.rest.service.redelivery.Context;
import com.spring.boot.rest.service.redelivery.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SoapGetArticleService extends DefaultAsyncService<GetArticleRequest, GetArticleResponse> {
    @Autowired
    private SoapAddArticleService soapAddArticleService;

    @Autowired
    public SoapGetArticleService(@Qualifier("soapGetArticleProvider") DataProvider<GetArticleRequest, GetArticleResponse> dataProvider,
                                 @Value("${retry.default.count}") int redeliveryCount,
                                 @Value("${retry.activate.second}") long activateSecond) {
        super(dataProvider, redeliveryCount, activateSecond);
    }

    @Override
    public void onSuccess(GetArticleResponse response, Context<GetArticleRequest> context) {
        Article article = response.getArticle();
        String newName = article.getName() + "new";
        AddArticleRequest addArticleRequest = new AddArticleRequest();
        addArticleRequest.setName(newName);
        addArticleRequest.setDescription("add new");
        soapAddArticleService.send(new Context<>(context, this.getClass().getSimpleName(), addArticleRequest));
    }

    public void send(String name) {
        GetArticleRequest request = new GetArticleRequest();
        request.setName(name);
        super.send(new Context<>(request));
    }
}

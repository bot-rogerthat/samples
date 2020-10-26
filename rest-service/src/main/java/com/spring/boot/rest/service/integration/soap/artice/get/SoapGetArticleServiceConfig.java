package com.spring.boot.rest.service.integration.soap.artice.get;

//@Configuration
public class SoapGetArticleServiceConfig {
//    @Autowired
//    @Qualifier("soapGetArticleProvider")
//    private DataProvider<GetArticleRequest, GetArticleResponse> dataProvider;
//    @Autowired
//    @Qualifier("soapGetArticleCallback")
//    private Callback<GetArticleRequest, GetArticleResponse> callback;
//    @Autowired
//    private RedeliveryService redeliveryService;
//    @Value("${retry.default.count}")
//    private int redeliveryCount;
//    @Value("${retry.activate.second}")
//    private long activateSecond;
//    @Value("${spring.application.name}")
//    private String appName;
//
//    @Bean
//    public DefaultAsyncService<GetArticleRequest, GetArticleResponse> soapGetArticleAsyncService() {
//        return new DefaultAsyncService<>(dataProvider,
//                redeliveryService,
//                redeliveryCount,
//                activateSecond,
//                callback,
//                appName + "_soapGetArticleAsyncService",
//                GetArticleRequest.class);
//    }
//
//    @Bean
//    public Queue soapGetArticleAsyncQueue() {
//        return new Queue(appName + "_soapGetArticleAsyncService",true);
//    }
}

var ioc = {
    esClient: {
        type: "org.nlpcn.elasticsearch.site.ESClient",
        args: ["127.0.0.1:9300"],
 //       args: [true, 'name:passowrd',"127.0.0.1:9300"],
        events: {
            depose: 'destroy'
        }
    }
};


package com.earl.auth;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BulkClass {

    @Test
    public void test1(){
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest = new IndexRequest("happy");
        bulkRequest.add(indexRequest);
        log.info("bulkRequest : {}", bulkRequest.requests());

    }
}

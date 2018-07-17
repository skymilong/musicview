package com.musicview.musicview.singer.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.musicview.musicview.common.utils.ESClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SingerService {
    @Autowired
    private ESClient esClient;

    public Map<String,Object> getSingerAdd() throws IOException {
        RestHighLevelClient client = esClient.getClient();

        Map<String,Object> stringObjectMap = new HashMap<String,Object>();


        TermsAggregationBuilder genders = AggregationBuilders.terms("genders").field("singer.country.keyword");
        SearchRequest request = new SearchRequest("mymusic").types("hotmusic3").source(new SearchSourceBuilder().aggregation(genders));
        System.out.println(request);
        SearchResponse response = client.search(request);
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();

        ParsedStringTerms aggregation = (ParsedStringTerms) aggMap.get("genders");
        Iterator<? extends Terms.Bucket> iterator = aggregation.getBuckets().iterator();
        while (iterator.hasNext()){
            Terms.Bucket next = iterator.next();
            String keyAsString = next.getKeyAsString();

            stringObjectMap.put(keyAsString.trim().equals("")?"未知":keyAsString,next.getDocCount());
        }
        System.out.println(stringObjectMap);
        return stringObjectMap;
    }
}

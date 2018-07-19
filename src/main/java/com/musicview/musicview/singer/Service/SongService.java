package com.musicview.musicview.singer.Service;

import com.musicview.musicview.common.utils.ESClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.support.IncludeExclude;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class SongService {
    @Autowired
    private ESClient esClient;

    private static final String USELESSWORDS1 = "吧、罢、呗、啵、的、价、啦、来、唻、了、嘞、哩、咧、咯、啰、喽、吗、嘛、嚜、么、哪、呢、呐、否、呵、哈、不、兮、般、则、连、罗、给、噻、哉、呸";

    public Map<String,Object> getWordsCloud() throws IOException {
        RestHighLevelClient client = esClient.getClient();


        IncludeExclude includeExclude = new IncludeExclude("[\u4E00-\u9FA5]+",null);

        TermsAggregationBuilder genders = AggregationBuilders.terms("wordcloud").field("lyric").size(5000).includeExclude(includeExclude);


        SearchRequest request = new SearchRequest("mymusic").types("hotmusic3").source(new SearchSourceBuilder().aggregation(genders));
        Map<String,Object> stringObjectMap = new HashMap<String,Object>();
        System.out.println(request);
        SearchResponse response = client.search(request);


        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        ParsedStringTerms aggregation = (ParsedStringTerms) aggMap.get("wordcloud");
        Iterator<? extends Terms.Bucket> iterator = aggregation.getBuckets().iterator();
        while (iterator.hasNext()) {
            Terms.Bucket next = iterator.next();
            String keyAsString = next.getKeyAsString();
            if(USELESSWORDS1.indexOf(keyAsString)>0){
                System.out.println("过滤："+keyAsString);
                continue;
            }
            long docCount = next.getDocCount();

            stringObjectMap.put(keyAsString,docCount);
        }
        return stringObjectMap;
    }
}

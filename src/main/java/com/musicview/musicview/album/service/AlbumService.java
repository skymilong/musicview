package com.musicview.musicview.album.service;

import com.musicview.musicview.common.utils.ESClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AlbumService {
    @Autowired
    private ESClient esClient;

   /* private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
    {
        public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
        {
            return n.getValue()-m.getValue();
        }
    }*/

    public Map<String,Object> getAlbumAdd() throws IOException{
        RestHighLevelClient client = esClient.getClient();
        Map<String,Object> stringObjectMap = new HashMap<String,Object>();

        TermsAggregationBuilder genders = AggregationBuilders.terms("genders").field("albumname.keyword");
        SearchRequest searchRequest=new SearchRequest("mymusic").types("hotmusic3")
                .source(new SearchSourceBuilder().aggregation(genders));
        
        System.out.println(searchRequest);
        SearchResponse response = client.search(searchRequest);
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        ParsedStringTerms aggregation = (ParsedStringTerms) aggMap.get("genders");
        Iterator<? extends Terms.Bucket> iterator = aggregation.getBuckets().iterator();
        while(iterator.hasNext()){
            Terms.Bucket next = iterator.next();
            String keyAsString = next.getKeyAsString();
            //Map<String,Object> stringObjectMap1 = new HashMap<String,Object>();
            //stringObjectMap1.put("album_num",next.getDocCount());
            ArrayList<Object> list = new ArrayList<>();
            list.add(next.getDocCount());
            stringObjectMap.put(keyAsString.trim().equals("")?"未知地区":keyAsString,list);
        }

        /*List<Map.Entry<String,Integer>> listmap=new ArrayList<>();
        stringObjectMap.entrySet()
        listmap.addAll();*/
        System.out.println(stringObjectMap);
        return stringObjectMap;


    }
}

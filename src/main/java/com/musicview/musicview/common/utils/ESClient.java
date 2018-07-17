package com.musicview.musicview.common.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class ESClient {


    @Value("${es.nodes}")
    private String esNodes;
    @Value("${es.userName}")
    private String userName;
    @Value("${es.password}")
    private String password;

    private RestClient s_LowClient = null;
    private RestHighLevelClient s_HighClient = null;

    public ESClient()
    {
    }

    public RestHighLevelClient getClient()
    {
        if(this.s_HighClient == null)
        {
            this.createClient();
        }

        return this.s_HighClient;
    }

    public RestClient getLowClient()
    {
        if(this.s_LowClient == null)
        {
            this.createClient();
        }

        return this.s_LowClient;
    }

    private void createClient()
    {
        if(this.s_LowClient == null || this.s_HighClient == null)
        {
            System.out.println("Creating client for Search!");

            try
            {
                List<HttpHost> hostArray = new ArrayList<HttpHost>();
                String[] nodes = esNodes.split(";");
                for(String item : nodes) {
                    String[] host = item.split(":");
                    if (host.length == 2 && !host[0].equals("") && !host[1].equals("")) {
                        hostArray.add(new HttpHost(host[0], Integer.parseInt(host[1]), "http"));
                    }
                }

                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
                s_LowClient = RestClient.builder(hostArray.toArray(new HttpHost[0])).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback()
                {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder)
                    {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();

                s_HighClient = new RestHighLevelClient(s_LowClient);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.err.println("Error occured while creating search client!");
            }
        }
    }

    @PreDestroy
    protected void destory() {
        System.out.println("destory the client");
        if(this.s_LowClient != null) {
            try {
                this.s_LowClient.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

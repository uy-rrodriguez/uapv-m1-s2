package fr.ceri.rrodriguez.playerdistribue.util;

import java.util.Arrays;
import java.net.URLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import fr.ceri.rrodriguez.playerdistribue.model.WSData;


public class WSUtil {

    public static String IP_WS = null;
    //public static String URL_WS = "http://10.120.12.117:30000/";
    public static String URL_WS = "http://<ip>:30000/";
    public static final String URL_GET_IP = "https://uapv-m1-s2-util.herokuapp.com/get_util_ip/";


    public static void getIPWS() throws Exception {
        URLConnection connection = new URL(URL_GET_IP).openConnection();
        InputStream response = connection.getInputStream();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(response));
        String ip = in.readLine();

        if (ip != null)
            WSUtil.IP_WS = ip;

        //return WSUtil.IP_WS;
    }

    public static WSData callWS(String urlPart) throws Exception {
        if (WSUtil.IP_WS == null) {
            WSUtil.getIPWS();
        }
        //WSUtil.IP_WS = "10.120.12.117";

        final String url = URL_WS.replaceAll("<ip>", WSUtil.IP_WS) + urlPart;
        //final String url = URL_WS + urlPart;

        // Pour gerer le timeout
        SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
        s.setReadTimeout(30000);
        s.setConnectTimeout(5000);
    
        RestTemplate restTemplate = new RestTemplate(s);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        //wsdata = restTemplate.getForObject(url, WSData.class);
        ResponseEntity<WSData> resp = restTemplate.exchange(url, HttpMethod.GET, entity, WSData.class);
        return resp.getBody();
    }

}

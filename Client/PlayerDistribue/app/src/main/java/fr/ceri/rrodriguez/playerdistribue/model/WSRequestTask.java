import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import fr.ceri.rrodriguez.playerdistribue.model.WSData;


public class WSRequestTask extends AsyncTask<Void, Void, WSData> {

    @Override
    protected WSData doInBackground(Void... params) {
        try {
            final String url = "http://rest-service.guides.spring.io/greeting";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            WSData wsdata = restTemplate.getForObject(url, WSData.class);
            return wsdata;
        }
        catch (Exception e) {
            Log.e("PlayerActivity", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(WSData wsdata) {
        //TextView greetingIdText = (TextView) findViewById(R.id.id_value);
        //TextView greetingContentText = (TextView) findViewById(R.id.content_value);
        //greetingIdText.setText(greeting.getId());
        //greetingContentText.setText(greeting.getContent());
    }

}

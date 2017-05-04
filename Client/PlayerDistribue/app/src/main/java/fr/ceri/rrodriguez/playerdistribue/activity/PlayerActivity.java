package fr.ceri.rrodriguez.playerdistribue.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// Communication avec le webservice
import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import fr.ceri.rrodriguez.playerdistribue.R;
import fr.ceri.rrodriguez.playerdistribue.model.ActivitySession;
import fr.ceri.rrodriguez.playerdistribue.model.WSData;


public class PlayerActivity extends AppCompatActivity
        implements FragmentNavigationDrawer.FragmentNavigationDrawerListener {

    private Toolbar mToolbar;
    private FragmentNavigationDrawer drawerFragment;

    // Pour gerer les variables de session
    private ActivitySession session;
    public ActivitySession getSession() {
        return this.session;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentNavigationDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        // Recuperation de l'etat anterieur de l'app
        if (savedInstanceState != null) {
            session = (ActivitySession) savedInstanceState.getSerializable("SESSION");
        }
        else {
            session = new ActivitySession();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar snackbar = Snackbar
                    .make((DrawerLayout) findViewById(R.id.drawer_layout),
                    "Click Main Menu Settings",
                    Snackbar.LENGTH_LONG);
            snackbar.show();

            return true;
        }

        else if (id == R.id.action_refresh) {
            new WSRequestTask().execute();

            Snackbar snackbar = Snackbar
                    .make((DrawerLayout) findViewById(R.id.drawer_layout),
                    "Click Main Menu Refresh",
                    Snackbar.LENGTH_LONG);
            snackbar.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);


            /*
            // Test SnackBar
            Snackbar snackbar = Snackbar
                    .make((DrawerLayout) findViewById(R.id.drawer_layout),
                    "Welcome to AndroidHive",
                    Snackbar.LENGTH_LONG);
            snackbar.show();
            */
        }
    }


    /**
     * Gestion de la session lors de la sauvegarde de l'etat de l'app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("SESSION", session);
    }



    /**
     * Gestion du webservice
     */

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

            Snackbar snackbar = Snackbar
                    .make((DrawerLayout) findViewById(R.id.drawer_layout),
                    "WS > Id : " + wsdata.getId() + "; Content : " + wsdata.getContent(),
                    Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

}

package lucasslemons.morse;


import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Base extends ActionBarActivity {
    public static final String TAG = "Base";

    private static Context createContext;
    private static int gameState = 2;

    //views
    private static RenderViewGame game;
    private static RenderView showStarter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createContext = this;

        setContentView(R.layout.activity_base);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(gameState == 0 || gameState == 1)
        switch (item.getItemId()) {
            case R.id.action_settings:
                switch (gameState){
                    case 0:
                        showStarter.clearTheWord();
                        break;
                    case 1:
                        game.clearTheWord();
                        break;
                    default:
                }
                return true;
            case R.id.helper_settings:
                switch (gameState){
                    case 0:
                        showStarter.checkUncheck();
                        if(item.isChecked())
                            item.setChecked(false);
                        else
                            item.setChecked(true);
                        break;
                    case 1:
                        game.checkUncheck();
                        if(item.isChecked())
                            item.setChecked(false);
                        else
                            item.setChecked(true);
                        break;
                    default:
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        else
            return true;
    }

    public static class GameFragment extends Fragment {

        //display content
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            game = new RenderViewGame(createContext);
            return game;
        }
    }
    public static class SandBoxFragment extends Fragment {

        //display content
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            showStarter = new RenderView(createContext);
            return showStarter;
        }
    }
    public static class HomeFragment extends Fragment {
        //display content
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            gameState = 3;
            return inflater.inflate(R.layout.fragment_home, container, false);
        }
    }
    //ad fragment
    public static class AdFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }

        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            AdView mAdView = (AdView) getView().findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();

            //deactivate ad for testing...
            mAdView.loadAd(adRequest);
        }
    }

    public void goToGame(View view){
        gameState = 1;
        Log.e(TAG, "go to game");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.homeFragment, new GameFragment()).addToBackStack("fragback").commit();
    }
    public void goToSandBox(View view){
        gameState = 0;
        Log.e(TAG, "go to sandbox");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.homeFragment, new SandBoxFragment()).addToBackStack("fragback").commit();
    }
}

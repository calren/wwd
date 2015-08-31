package caren.android.com.wwd;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import caren.android.com.wwd.UserListActivity.UserModel;
import caren.android.com.wwd.UserListActivity.UsersAdapter;
import caren.android.com.wwd.networking.MixpanelApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvUsers);
        UsersAdapter adapter = new UsersAdapter(getUsers());
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<UserModel> getUsers() {
        List<UserModel> userModels = new ArrayList<>();
        try {
            JSONArray result = MixpanelApi.fetchEngageResults();
            for (int i = 0; i < result.length(); i++) {
                JSONObject jsonObject = result.getJSONObject(i);
                UserModel userModel = new UserModel();
                jsonObject.getString("distint_id");
                JSONObject jsonObjectProperties = jsonObject.getJSONObject("properties");
                userModel.setEmail(jsonObjectProperties.getString("email"));
                System.out.println("email : " + jsonObjectProperties.getString("email"));
                userModels.add(userModel);
            }
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
        return userModels;
    }
}

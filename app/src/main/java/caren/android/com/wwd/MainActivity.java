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
    UsersAdapter adapter = new UsersAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeRecyclerView();
        new MixpanelApi().execute(this);
    }

    private void initializeRecyclerView() {
        System.out.println("initialize recycler view called");
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvUsers);
        new MixpanelApi().execute(this);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    public void populateUserList(JSONArray jsonArray) {
        System.out.println("populate users called");
        List<UserModel> userModels = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UserModel userModel = new UserModel();
                jsonObject.has("$distint_id");
                JSONObject jsonObjectProperties = jsonObject.getJSONObject("$properties");
                if (jsonObjectProperties.has("$email")) {
                    userModel.setEmail(jsonObjectProperties.getString("$email"));
                }
                if (jsonObjectProperties.has("$phone")) {
                    userModel.setPhone(jsonObjectProperties.getString("$phone"));
                }
                if (jsonObjectProperties.has("$name")) {
                    userModel.setName(jsonObjectProperties.getString("$name"));
                }
                if (jsonObjectProperties.has("$location")) {
                    userModel.setLocation(jsonObjectProperties.getString("$location"));
                }

                userModels.add(userModel);
            }

            System.out.println("user models size: "+ userModels.size());
            adapter.addAll(userModels);
            System.out.println("adapter size: " + adapter.getItemCount());
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            System.out.println("exception: " + e);
        }

    }
}

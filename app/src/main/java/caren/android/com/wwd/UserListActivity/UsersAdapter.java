package caren.android.com.wwd.UserListActivity;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import caren.android.com.wwd.R;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<UserModel> userModels;

    public UsersAdapter(List<UserModel> users) {
        userModels = users;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.user_item_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder viewHolder, int position) {
        UserModel user = userModels.get(position);

        viewHolder.nameTextView.setText(user.getName());
        viewHolder.locationTextView.setText(user.getLocation());
        viewHolder.emailTextView.setText(user.getEmail());
        viewHolder.phoneTextView.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView emailTextView;
        public TextView phoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.user_name);
            locationTextView = (TextView) itemView.findViewById(R.id.user_location);
            emailTextView = (TextView) itemView.findViewById(R.id.user_email);
            phoneTextView = (TextView) itemView.findViewById(R.id.user_phone);
        }
    }


}

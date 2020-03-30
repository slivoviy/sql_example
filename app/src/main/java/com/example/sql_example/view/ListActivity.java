package com.example.sql_example.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sql_example.R;
import com.example.sql_example.domain.Friendship;
import com.example.sql_example.domain.User;
import com.example.sql_example.interactor.UsersInteractor;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    public static User user;
    public static UsersInteractor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        interactor = new UsersInteractor(this);
        user = getIntent().getParcelableExtra("User");

        RecyclerView recyclerView = findViewById(R.id.recycler);
        ArrayList<User> users = interactor.getUsers();
        users.remove(Integer.parseInt(user.id) - 1);
        RecyclerAdapter adapter = new RecyclerAdapter(this, users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setAppbarHeading(user.name);
    }

    private void setAppbarHeading(String heading) {
        getSupportActionBar().setTitle(heading);
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<UserVH> {
    private final Activity context;
    private final ArrayList<User> userList;

    RecyclerAdapter(Activity context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View userView = inflater.inflate(R.layout.user_item, null, false);
        return new UserVH(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}


class UserVH extends RecyclerView.ViewHolder {
    private final TextView login;
    private final Button friendButton;
    private final TextView wannaBe;

    UserVH(@NonNull View itemView) {
        super(itemView);

        login = itemView.findViewById(R.id.login);
        friendButton = itemView.findViewById(R.id.friendButton);
        wannaBe = itemView.findViewById(R.id.wannaBe);
    }

    void bind(User user) {
        login.setText(user.name);
        switch (ListActivity.interactor.checkFriendship(ListActivity.user.id, user.id)) {
            case 0:
                friendButton.setClickable(true);
                friendButton.setText("Добавить");
                friendButton.setBackgroundResource(android.R.drawable.btn_default);
                friendButton.setOnClickListener(v -> {
                    ListActivity.interactor.insertFriendship(ListActivity.user.id, user.id);
                    friendButton.setClickable(false);
                    friendButton.setText("Ожидание");
                    friendButton.setBackground(null);
                });
                break;
            case -1:
                Friendship friendship = ListActivity.interactor.getFriendship(ListActivity.user.id, user.id);
                if(friendship.firstId.equals(ListActivity.user.id)) {
                    friendButton.setClickable(false);
                    friendButton.setText("Ожидание");
                    friendButton.setBackground(null);
                } else if(friendship.firstId.equals(user.id)) {
                    wannaBe.setVisibility(View.VISIBLE);
                    String wannaBeText = user.name + " " +
                            login.getContext().getResources().getString(R.string.wanna_be);
                    wannaBe.setText(wannaBeText);
                    friendButton.setClickable(true);
                    friendButton.setText("Добавить");
                    friendButton.setBackgroundResource(android.R.drawable.btn_default);
                    friendButton.setOnClickListener(v -> {
                        wannaBe.setVisibility(View.INVISIBLE);
                        ListActivity.interactor.confirmFriendship(ListActivity.user.id, user.id);
                        friendButton.setText("Удалить");
                        friendButton.setOnClickListener(w -> {
                            ListActivity.interactor.deleteFriendship(ListActivity.user.id, user.id);
                            friendButton.setText("Добавить");
                            friendButton.setOnClickListener(x -> {
                                ListActivity.interactor.insertFriendship(ListActivity.user.id, user.id);
                                friendButton.setClickable(false);
                                friendButton.setText("Ожидание");
                                friendButton.setBackground(null);
                            });
                        });
                    });
                }
                break;
            case 1:
                friendButton.setBackgroundResource(android.R.drawable.btn_default);
                friendButton.setClickable(true);
                friendButton.setText("Удалить");
                friendButton.setOnClickListener(w -> {
                    ListActivity.interactor.deleteFriendship(ListActivity.user.id, user.id);
                    friendButton.setText("Добавить");
                    friendButton.setOnClickListener(x -> {
                        ListActivity.interactor.insertFriendship(ListActivity.user.id, user.id);
                        friendButton.setClickable(false);
                        friendButton.setText("Ожидание");
                        friendButton.setBackground(null);
                    });
                });
                break;
        }
    }
}
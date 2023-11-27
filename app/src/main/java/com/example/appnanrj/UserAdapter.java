package com.example.appnanrj;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private static final int MENU_DELETE = 1;
    private static final int MENU_PASS_CHANGE = 2;

    private List<User> users; // список пользователей

    private UserClickListener listener;

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    public void setData(List<User> userList) {
        users = userList;
        notifyDataSetChanged();
    }

    public void setListener(UserClickListener listener) {
        this.listener = listener;
    }

    public interface UserClickListener {
        void onItemClick(User user);
        void onMenuDeleteClick(User user);
        void onMenuPassChangeClick(User user);
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvLogin;
        private TextView tvPass;
        private ImageButton ibMore;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvLogin = itemView.findViewById(R.id.tv_login);
            tvPass = itemView.findViewById(R.id.tv_pass);
            ibMore = itemView.findViewById(R.id.ib_more);

            ibMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(users.get(getAdapterPosition()), v);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(users.get(getAdapterPosition()));
                }
            });
        }

        void bind(User user) {
            tvId.setText(String.valueOf(user.getId()));
            tvLogin.setText(user.getLogin());
            tvPass.setText(user.getPass());
        }

        private void showPopupMenu(User user, View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);

            popupMenu.getMenu().add(Menu.NONE, MENU_DELETE, Menu.NONE, "Удалить");
            popupMenu.getMenu().add(Menu.NONE, MENU_PASS_CHANGE, Menu.NONE, "Изменить пароль");

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case MENU_DELETE:
                            listener.onMenuDeleteClick(user);
                            break;
                        case MENU_PASS_CHANGE:
                            listener.onMenuPassChangeClick(user);
                            break;
                    }
                    return true;
                }
            });

            popupMenu.show();
        }
    }
}

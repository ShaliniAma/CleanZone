package com.s23010457.cleanzone.ui.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.s23010457.cleanzone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_notifications);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Notification> notifications = loadNotifications();
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        rv.setAdapter(adapter);

        return view;
    }

    private List<Notification> loadNotifications() {
        List<Notification> list = new ArrayList<>();
        try {
            InputStream is = requireContext().getAssets().open("notifications.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                String message = obj.getString("message");
                list.add(new Notification(title, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

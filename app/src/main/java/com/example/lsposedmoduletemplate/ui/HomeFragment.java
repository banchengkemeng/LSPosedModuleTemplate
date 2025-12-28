package com.example.lsposedmoduletemplate.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lsposedmoduletemplate.R;
import com.example.lsposedmoduletemplate.WebServerManager;
import com.example.lsposedmoduletemplate.service.HttpService;
import com.example.lsposedmoduletemplate.utils.IntentUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private MaterialButton btn;
    private TextInputEditText etHost;
    private TextInputEditText etPort;

    private void showErrorDialog(String errorMessage) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("启动服务器失败")
                .setMessage("错误详情：\n" + errorMessage)
                .setPositiveButton("我知道了", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private final BroadcastReceiver errorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (Objects.requireNonNull(action)) {
                case IntentUtil.ACTION_ERROR: {
                    String msg = intent.getStringExtra(IntentUtil.ERR_MSG_KEY);
                    showErrorDialog(msg);
                    return;
                }
                case WebServerManager.ACTION_SERVER_START_SUCCESS: {
                    btn.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(
                                    com.google.android.material.R.color.design_default_color_error,
                                    null
                            ))
                    );
                    btn.setText("停止服务器");
                    return;
                }
                case WebServerManager.ACTION_SERVER_STOP_SUCCESS: {
                    btn.setBackgroundTintList(
                            ColorStateList.valueOf(getResources().getColor(
                                    com.google.android.material.R.color.design_default_color_primary,
                                    null
                            ))
                    );
                    btn.setText("启动服务器");
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn = view.findViewById(R.id.btn_server_control);
        etHost = view.findViewById(R.id.et_host);
        etPort = view.findViewById(R.id.et_port);

        btn.setOnClickListener(v -> {
            if (WebServerManager.isRunning()) {
                Intent intent = new Intent(getActivity(), HttpService.class);
                requireActivity().stopService(intent);
            } else {
                Intent intent = new Intent(getActivity(), HttpService.class);
                intent.putExtra("extra_host", Objects.requireNonNull(etHost.getText()).toString());
                intent.putExtra("extra_port", Integer.parseInt(Objects.requireNonNull(etPort.getText()).toString()));
                requireActivity().startService(intent);
            }
        });

        return view;
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentUtil.ACTION_ERROR);
        intentFilter.addAction(WebServerManager.ACTION_SERVER_START_SUCCESS);
        intentFilter.addAction(WebServerManager.ACTION_SERVER_STOP_SUCCESS);
        requireContext().registerReceiver(errorReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireContext().unregisterReceiver(errorReceiver);
    }
}
package com.kurye.kurye.screen.filter.editText;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.kurye.kurye.databinding.FragmentEditTextBinding;
import com.kurye.kurye.entity.response.ItemEntity;
import com.kurye.kurye.task.ItemTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTextFragment extends Fragment {
    private static final String ARG_HINT = "arg.hint";
    private VMEditTextFragment vmEditTextFragment;
    private FragmentEditTextBinding binding;
    private OnItemSelectedListener listener;

    public EditTextFragment() {
    }

    public static EditTextFragment newInstance(String hint) {
        EditTextFragment fragment = new EditTextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HINT, hint);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditTextBinding.inflate(inflater, container, false);
        vmEditTextFragment = ViewModelProviders.of(this).get(VMEditTextFragment.class);
        if (getArguments() != null) {
            String hint = getArguments().getString(ARG_HINT);
            vmEditTextFragment.setHint(hint);
        }

        fetchItems();
        binding.text.setOnItemClickListener((parent, view, position, id) -> {
            if (listener!=null){
                listener.onSelect(((ArrayAdapter<ItemEntity>) binding.text.getAdapter()).getItem(position));
            }
        });

        binding.setVm(vmEditTextFragment);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    private void fetchItems() {
        ArrayAdapter<ItemEntity> adapter = new ArrayAdapter<ItemEntity>(getContext(),
                android.R.layout.simple_dropdown_item_1line);
        ItemTask.getInstance().fetch((status, message) -> {
            if (status == ItemTask.SUCCESS) {
                adapter.addAll(ItemTask.getInstance().load());
                vmEditTextFragment.getAdapter().set(adapter);
                vmEditTextFragment.getEnabled().set(true);
            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnItemSelectedListener {
        void onSelect(ItemEntity entity);
    }
}

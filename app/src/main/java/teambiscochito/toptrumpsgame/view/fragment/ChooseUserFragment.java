package teambiscochito.toptrumpsgame.view.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import teambiscochito.toptrumpsgame.R;
import teambiscochito.toptrumpsgame.model.room.pojo.User;
import teambiscochito.toptrumpsgame.view.adapter.RecyclerUserAdapter;
import teambiscochito.toptrumpsgame.viewmodel.ViewModel;

public class ChooseUserFragment extends Fragment {
    RecyclerView recyclerView;
    ViewModel viewModel;

    public ChooseUserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        recyclerView = getView().findViewById(R.id.recyclerView);
        List<User> userList = viewModel.getUserList();
        Log.v("xyzyx", "ARRAY DEVUELTO POR EL VIEWMODEL: " + userList.toString());
        //la foto de avatares numero 0 deberia de ser un simbolo de suma
        //aunque sea de tipo usuario, se usa como botón de añadir usuario
        userList.add(new User("Create new user", 0));

        TypedArray avatarImages = getResources().obtainTypedArray(R.array.avatar);
        RecyclerUserAdapter recyclerUserAdapter = new RecyclerUserAdapter(userList, this, view, viewModel, avatarImages);
        recyclerView.setAdapter(recyclerUserAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
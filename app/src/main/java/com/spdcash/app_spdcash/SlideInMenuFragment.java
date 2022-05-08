package com.spdcash.app_spdcash;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SlideInMenuFragment extends DialogFragment {
    public SlideInMenuFragment() {
        // Required empty public constructor
    }

    public static SlideInMenuFragment newInstance() {
        SlideInMenuFragment fragment = new SlideInMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_slide_in_menu);

        //Aligner justifié à gauche dans la boîte de dialogue plein écran
        Window window = dialog.getWindow();
        window.getAttributes().windowAnimations = R.style.SlideInMenuAnimation;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.TOP | Gravity.START);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //Définir la vue racine de la mise en page
        FrameLayout rootView = dialog.findViewById(R.id.slide_menu);
        onViewCreated(rootView, savedInstanceState);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] menuArray = {"Home", "profil", "Déconnexion"};
        //Définir le contenu du menu
        ListView menu = view.findViewById(R.id.menu_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, menuArray);
        menu.setAdapter(adapter);
    }
}

package nsit.com.canteenapp.IntroScreens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nsit.com.canteenapp.LogInScreen;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.SignUpScreen;

/**
 * Created by starhawk on 24/07/18.
 */

public class IntroScreenFragmentFive extends Fragment {

    ImageView imageView5;
    Button getStartedBtn;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.intro_screen_5,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView5 = getActivity().findViewById(R.id.imageView5);
        getStartedBtn = getActivity().findViewById(R.id.getStartedBtn);
        Glide.with(getActivity()).load(R.drawable.discounts_intro_image).into(imageView5);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LogInScreen.class);
                startActivity(intent);
            }
        });

    }

}

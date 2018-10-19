package nsit.com.canteenapp.IntroScreens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import nsit.com.canteenapp.R;

/**
 * Created by starhawk on 24/07/18.
 */

public class IntroScreenFragmentOne extends Fragment {
    ImageView imageView1;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.intro_screen_1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView1 = getActivity().findViewById(R.id.usernameImageView);
        Glide.with(getActivity()).load(R.drawable.save_time_intro_image).into(imageView1);
    }
}

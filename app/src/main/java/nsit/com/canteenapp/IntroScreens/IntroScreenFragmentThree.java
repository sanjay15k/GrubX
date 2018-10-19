package nsit.com.canteenapp.IntroScreens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import nsit.com.canteenapp.R;

/**
 * Created by starhawk on 24/07/18.
 */

public class IntroScreenFragmentThree extends Fragment {

    ImageView imageView3;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.intro_screen_3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView3 = getActivity().findViewById(R.id.imageView3);
        Glide.with(getActivity()).load(R.drawable.suggestions_intro_image).into(imageView3);
    }

}

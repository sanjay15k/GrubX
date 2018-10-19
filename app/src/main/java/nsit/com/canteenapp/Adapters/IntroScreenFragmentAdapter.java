package nsit.com.canteenapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nsit.com.canteenapp.IntroScreens.IntroScreenFragmentFive;
import nsit.com.canteenapp.IntroScreens.IntroScreenFragmentFour;
import nsit.com.canteenapp.IntroScreens.IntroScreenFragmentOne;
import nsit.com.canteenapp.IntroScreens.IntroScreenFragmentThree;
import nsit.com.canteenapp.IntroScreens.IntroScreenFragmentTwo;

/**
 * Created by starhawk on 24/07/18.
 */

public class IntroScreenFragmentAdapter extends FragmentPagerAdapter {

    private static int NO_OF_SLIDES = 5;

    public IntroScreenFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : IntroScreenFragmentOne fragmentOne = new IntroScreenFragmentOne();
                    return fragmentOne;
            case 1 : IntroScreenFragmentTwo fragmentTwo = new IntroScreenFragmentTwo();
                    return fragmentTwo;
            case 2 : IntroScreenFragmentThree fragmentThree = new IntroScreenFragmentThree();
                return fragmentThree;
            case 3 : IntroScreenFragmentFour fragmentFour = new IntroScreenFragmentFour();
                return fragmentFour;
            case 4 : IntroScreenFragmentFive fragmentFive = new IntroScreenFragmentFive();
                return fragmentFive;
            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return NO_OF_SLIDES;
    }

}

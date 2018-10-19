package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nsit.com.canteenapp.DTO.OurTeamProfileDTO;
import nsit.com.canteenapp.R;

/**
 * Created by starhawk on 12/08/18.
 */

public class OurProfileTeamViewPagerAdapter extends PagerAdapter {

    private ArrayList<OurTeamProfileDTO> ourTeamProfileDTOArrayList;
    Context context;

    public OurProfileTeamViewPagerAdapter(ArrayList<OurTeamProfileDTO> ourTeamProfileDTOArrayList, Context context) {
        this.ourTeamProfileDTOArrayList = ourTeamProfileDTOArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.profile_single_layout,container,false);

        ImageView profileImage = view.findViewById(R.id.profileImage);
        TextView memberNameTextView = view.findViewById(R.id.memeberNameTextView);
        TextView memberPositionTextView = view.findViewById(R.id.memberPositionTextView);
        ImageView facebookProfileImageView = view.findViewById(R.id.facebookProfileImageView);
        ImageView emailProfileImageView = view.findViewById(R.id.emailProfileImageView);
        ImageView whatsappProfileImageView = view.findViewById(R.id.whatsappProfileImageView);

        profileImage.setImageResource(ourTeamProfileDTOArrayList.get(position).getProfileImage());
        memberNameTextView.setText(ourTeamProfileDTOArrayList.get(position).getMemberName());
        memberPositionTextView.setText(ourTeamProfileDTOArrayList.get(position).getMemberPosition());
        facebookProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+ourTeamProfileDTOArrayList.get(position).getMemberFacebook())));
                } catch (PackageManager.NameNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ourTeamProfileDTOArrayList.get(position).getMemberFacebook())));
                }
            }
        });

        emailProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, ourTeamProfileDTOArrayList.get(position).getMemberEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                context.startActivity(Intent.createChooser(intent, "Send email via"));
            }
        });

        whatsappProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("smsto:" + ourTeamProfileDTOArrayList.get(position).getMemberWhatsapp());
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                context.startActivity(Intent.createChooser(i, "Send Message via!"));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return ourTeamProfileDTOArrayList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}

package com.ndusenior.fix.AssistantClasses;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ndusenior.fix.R;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import static android.R.attr.id;
import static android.R.attr.layout_alignParentRight;
import static android.R.attr.layout_gravity;

/**
 * Created by antho on 4/17/2017.
 */

public class UserConversation {

    private Context theMainContext;
    private RelativeLayout theLayout;

    private int layoutNumber ;
    private File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    private String profileImage = rootFolder.getAbsolutePath() + "/profile.jpg";


    public UserConversation(Context context, RelativeLayout LayoutToExtend){

        theLayout = LayoutToExtend;
        theMainContext = context;
    }


    public int CreateUserSpeech(int LayoutNumberYouAreOn, String UserText){

        LinearLayout newLayout = new LinearLayout(theMainContext);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);


        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        p.addRule(RelativeLayout.BELOW,LayoutNumberYouAreOn);




        // Create a TextView programmatically.

        TextView userSpeech = CreateUserTextView(theMainContext);
        userSpeech.setText(UserText);
        newLayout.addView(userSpeech);


        // ---------- Then I will create the triangle ------------//

        ImageView theTriangle = CreateUserArrow(theMainContext);
        newLayout.addView(theTriangle);

        //lastly I need to create the profile image
        ImageView userImage = CreateUserDP(theMainContext);
        newLayout.addView(userImage);



        layoutNumber = LayoutNumberYouAreOn + 1;
        // Add newly created TextView to parent view group (RelativeLayout)

        newLayout.setId(layoutNumber);
        newLayout.setLayoutParams(p);
        newLayout.setPadding(0,30,0,0);

        theLayout.addView(newLayout);

        return layoutNumber;

    }

    private ImageView CreateUserDP(Context context){



        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());


        ImageView userImage = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(0,0,0,0);
        layoutParams.weight = 3;

        userImage.setLayoutParams(layoutParams);
        userImage.setId(R.id.UserDp);

        userImage.setImageBitmap(BitmapFactory.decodeFile(profileImage));

        return userImage;
    }
    private ImageView CreateUserArrow(Context context){

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int marginleft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -25, context.getResources().getDisplayMetrics());


        LinearLayout.LayoutParams ArrowLayout = new LinearLayout.LayoutParams(width, height);
        ArrowLayout.setMargins(marginleft, 10,10,10);
        ArrowLayout.weight = 1;

        ImageView theTriangle = new ImageView(context);
        theTriangle.setLayoutParams(ArrowLayout);

        theTriangle.setImageResource(R.drawable.arrow_bg1);



        return theTriangle;
    }
    private TextView CreateUserTextView(Context context){




        TextView userSpeech = new TextView(context);
        LinearLayout.LayoutParams Parameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT);


        Parameters.weight = 6;
        Parameters.setMargins(0,10,0,0);

        // Apply the layout parameters to TextView widget
        userSpeech.setLayoutParams(Parameters);
        // Set text to display in TextView
        // Set a text color for TextView text
        userSpeech.setTextColor(Color.parseColor("#000000"));
        userSpeech.setBackgroundResource(R.drawable.rounded_corner);
        userSpeech.setPadding(40,40,40,40);

        return userSpeech;
    }


}

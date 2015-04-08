package com.filipvinkovic.MaterialAnimationsDemo;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class DetailActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "DetailActivity:image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setEnterTransition(makeEnterTransition());
        postponeEnterTransition();

        ImageView image = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(getIntent().getStringExtra("IMAGE_EXTRA")).into(image,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        DetailActivity.this.startPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {}
                });

        final View rootView = findViewById(R.id.detail_root);
        final ViewTreeObserver observer = rootView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                circularReveal();
            }
        });


        getWindow().setReturnTransition(makeReturnTransition());
    }


    private Transition makeEnterTransition() {
        TransitionSet enterTransition = new TransitionSet();

        // Slide the text off the bottom of the screen.
        Transition bottomSlide = new Slide(Gravity.BOTTOM);
        bottomSlide.addTarget(findViewById(R.id.text_container));
        enterTransition.addTransition(bottomSlide);

        enterTransition.setDuration(300);
        return enterTransition;
    }

    private Transition makeReturnTransition() {
        TransitionSet returnTransition = new TransitionSet();

        // Slide the image background to the top of the screen.
        Transition topSlide = new Slide(Gravity.TOP);
        topSlide.addTarget(findViewById(R.id.image_bg));
        returnTransition.addTransition(topSlide);

        // Slide the cards to the bottom of the screen.
        Transition bottomSlide = new Slide(Gravity.BOTTOM);
        bottomSlide.addTarget(findViewById(R.id.text_container));
        returnTransition.addTransition(bottomSlide);

        // Fade out the toolbar
        Transition fade = new Fade();
        fade.addTarget(findViewById(R.id.toolbar));
        returnTransition.addTransition(fade);

        returnTransition.setDuration(300);
        return returnTransition;
    }

    private void circularReveal() {
        View myView = findViewById(R.id.image_bg);

        // get the center for the clipping circle
        int startX = (myView.getLeft() + myView.getRight()) / 2;
        int startY = (myView.getTop() + myView.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, startX, startY, 0, finalRadius);
        anim.setDuration(700);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }


    @Override protected int getLayoutResource() {
        return R.layout.activity_detail;
    }
}

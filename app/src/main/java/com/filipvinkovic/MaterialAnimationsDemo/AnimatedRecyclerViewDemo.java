package com.filipvinkovic.MaterialAnimationsDemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimatedRecyclerViewDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CustomAdapter(this));
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
    {
        private Context context;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView text;
            ImageView image;

            public ViewHolder(View itemView)
            {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.text);
                image = (ImageView) itemView.findViewById(R.id.image);
            }
        }

        public CustomAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            runEnterAnimation(holder.itemView, position);

            holder.text.setText("List item #" + (position + 1));
        }

        private void runEnterAnimation(View view, int position) {
            if (position > lastPosition) {
                lastPosition = position;
                view.setTranslationX(600);
                view.setAlpha(0.f);
                view.animate()
                        .translationX(0).alpha(1.f)
                        .setStartDelay(70 * position)
                        .setInterpolator(new DecelerateInterpolator(2.f))
                        .setDuration(700)
                        .start();
            }
        }


    }
}

package me.danielpan.youtubelike.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.danielpan.youtubelike.NbaBriefActivity;
import me.danielpan.youtubelike.R;
import me.danielpan.youtubelike.SongListActivity;
import me.danielpan.youtubelike.VersionIntroductionActivity;

/**
 * Created by it-od-m-2572 on 15/6/9.
 */
public class CstmAdapter extends RecyclerView.Adapter<CstmAdapter.ViewHolder> {
    private Context context;
    private String[] titles;
    private int type;
    public static final int TYPE_VERSION = 0X000;
    public static final int TYPE_MUSIC = 0X001;
    public static final int TYPE_SPORT = 0X002;

    public CstmAdapter(Context ctxt, String[] resArray, int type) {
        super();
        context = ctxt;
        titles = resArray;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_VERSION:
                        context.startActivity(new Intent(context, VersionIntroductionActivity.class));
                        break;
                    case TYPE_MUSIC:
                        context.startActivity(new Intent(context, SongListActivity.class));

                        break;
                    case TYPE_SPORT:
                        context.startActivity(new Intent(context, NbaBriefActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_layout, null, false);
        ViewHolder holder = new ViewHolder(itemView);
        itemView.setTag(holder);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}

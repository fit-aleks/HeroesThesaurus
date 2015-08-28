package com.fitaleks.heroesthesaurus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitaleks.heroesthesaurus.data.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderkulikovskiy on 20.06.15.
 */
public class CharatersListAdapter extends RecyclerView.Adapter<CharatersListAdapter.PhotoViewHolder> {
    private static final String LOG_TAG = CharatersListAdapter.class.getSimpleName();
    private List<Character> mDataset;

    public CharatersListAdapter(List<Character> array) {
        this.mDataset = array;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Character character = mDataset.get(position);
        holder.mName.setText(character.name);
        holder.mDescription.setText(character.description);
        Picasso.with(holder.mImageView.getContext())
                .load(character.imageUrl)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }

    public void addCharacters(List<Character> newCharacters) {
        this.mDataset.addAll(newCharacters);
        notifyDataSetChanged();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        public ImageView mImageView;
        @Bind(R.id.description)
        public TextView mDescription;
        @Bind(R.id.name)
        public TextView mName;

        public PhotoViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}

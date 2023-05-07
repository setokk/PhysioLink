package com.mobile.physiolink.ui.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DecorationSpacingItem extends RecyclerView.ItemDecoration {

    private int spacing;

    public DecorationSpacingItem(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        // add spacing to all items except the last one
        if (itemPosition < itemCount - 1) {
            outRect.bottom = spacing;
        }
        // add extra spacing only to the last item
        else {
            outRect.bottom = spacing * 25; // adjust the spacing as needed
        }
    }
}

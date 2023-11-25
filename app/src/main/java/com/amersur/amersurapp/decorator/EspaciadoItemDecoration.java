package com.amersur.amersurapp.decorator;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EspaciadoItemDecoration extends RecyclerView.ItemDecoration {

    private final int espaciado;

    public EspaciadoItemDecoration(Context context, int espaciado) {
        this.espaciado = espaciado;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Aplicar espacio solo a los elementos, no al último
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = espaciado;
        }
    }

}

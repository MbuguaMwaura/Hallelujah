package com.example.hallelujah.util;


    public interface ItemTouchHelperAdapter{
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }


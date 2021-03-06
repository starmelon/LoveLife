package com.starmelon.lovelife.util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

/**
 * Created by starmelon on 2016/11/29 0029.
 */

public class PicassoImageGetter implements Html.ImageGetter {

    final Resources resources;

    final Picasso pablo;

    final TextView textView;

    public PicassoImageGetter(final TextView textView, final Resources resources, final Picasso pablo) {
        this.textView = textView;
        this.resources = resources;
        this.pablo = pablo;
    }

    @Override
    public Drawable getDrawable(final String source) {

        final BitmapDrawablePlaceHolder result = new BitmapDrawablePlaceHolder();

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(final Void... meh) {
                try {
                    return pablo.load(source).get();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                try {
                    final BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);

                    BigDecimal width_origin = new BigDecimal(drawable.getIntrinsicWidth());
                    BigDecimal height_origin = new BigDecimal(drawable.getIntrinsicHeight());

                    if (width_origin.compareTo(height_origin) == 1){

                    }

                    BigDecimal width = new BigDecimal(textView.getMeasuredWidth());

                    BigDecimal height = width.multiply(height_origin).divide(width_origin,2);

                    drawable.setBounds(0, 0, width.intValue(), height.intValue());

                    result.setDrawable(drawable);

                    textView.getMeasuredWidth();
                    result.setBounds(0, 0, width.intValue(), height.intValue());

                    textView.setText(textView.getText()); // invalidate() doesn't work correctly...
                } catch (Exception e) {
                /* nom nom nom*/
                }
            }

        }.execute((Void) null);

        return result;
    }

    static class BitmapDrawablePlaceHolder extends BitmapDrawable {

        protected Drawable drawable;

        @Override
        public void draw(final Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

    }
}

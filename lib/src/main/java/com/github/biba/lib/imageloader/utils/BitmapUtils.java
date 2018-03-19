package com.github.biba.lib.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.github.biba.lib.contracts.IResponseConverter;
import com.github.biba.lib.imageloader.request.IImageRequest;
import com.github.biba.lib.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BitmapUtils {

    static Bitmap getBitmap(final InputStream pInputStream) {
        return BitmapFactory.decodeStream(pInputStream);
    }

    static Bitmap getScaledBitmap(final InputStream pInputStream, final int pWidth, final int pHeight) throws IOException {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(pInputStream.available());
        final byte[] chunk = new byte[1 << 16];
        int bytesRead;

        while ((bytesRead = pInputStream.read(chunk)) > 0) {
            byteArrayOutputStream.write(chunk, 0, bytesRead);
        }

        final byte[] bytes = byteArrayOutputStream.toByteArray(); //we use byteArray because we'll need options

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, pWidth, pHeight);
        options.inJustDecodeBounds = false;

        // Decode bitmap with inSampleSize set
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

    }

    public static Bitmap getBitmapFromFile(final File pFile) throws IOException {
        if (pFile != null) {
            final FileInputStream inputStream = new FileInputStream(pFile);
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            IOUtils.close(inputStream);
            return bitmap;
        }
        return null;
    }

    public static Bitmap getRoundBitmap(final Bitmap pBitmap) {
        final int width = pBitmap.getWidth();
        final int height = pBitmap.getHeight();
        final int radius = calculateRadius(width, height);
        final Bitmap output = Bitmap.createBitmap(radius,
                radius, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(pBitmap, rect, rect, paint);

        return output;
    }

    private static int calculateInSampleSize(final BitmapFactory.Options pOptions, final int pRequiredWidth, final int pRequiredHeight) {

        int sampleSize = 1;
        final int width;
        final int height;

        width = pOptions.outWidth;
        height = pOptions.outHeight;

        if (width > pRequiredWidth || height > pRequiredWidth) {

            final int heightRatio;
            final int widthRatio;

            if (pRequiredHeight == 0) {
                sampleSize = (int) Math.floor((float) width / (float) pRequiredWidth);
            } else if (pRequiredWidth == 0) {
                sampleSize = (int) Math.floor((float) height / (float) pRequiredHeight);
            } else {
                heightRatio = (int) Math.floor((float) height / (float) pRequiredHeight);
                widthRatio = (int) Math.floor((float) width / (float) pRequiredWidth);
                sampleSize = Math.max(heightRatio, widthRatio);
            }
        }

        return sampleSize;
    }

    public static class BitmapConverter implements IResponseConverter<Bitmap, InputStream> {

        private final IImageRequest mImageRequest;

        public BitmapConverter(final IImageRequest pImageRequest) {
            mImageRequest = pImageRequest;
        }

        @Override
        public Bitmap convert(final InputStream pInputStream) throws IOException {

            final int imageWidth = ImageUtils.getImageWidth(mImageRequest);
            final int imageHeight = ImageUtils.getImageHeight(mImageRequest);

            if (imageHeight <= 0 || imageWidth <= 0) {
                if (mImageRequest.isSaved()) {
                    return getBitmap(pInputStream);
                } else {
                    return null;
                }
            }
            if (mImageRequest.isScaled()) {
                return getScaledBitmap(pInputStream, imageWidth, imageHeight);
            } else {
                return getBitmap(pInputStream);
            }

        }

    }

    private static int calculateRadius(final int pWidth, final int pHeight) {
        return Math.min(pWidth, pHeight);
    }
}

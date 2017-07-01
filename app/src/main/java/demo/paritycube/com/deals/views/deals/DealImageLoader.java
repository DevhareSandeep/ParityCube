package demo.paritycube.com.deals.views.deals;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import demo.paritycube.com.deals.R;

/**
 * Created by Sandeep Devhare @APAR on 6/24/2017.
 */

public class DealImageLoader extends AsyncTask<Void, Void, File>
{
    private File m_file;
    private String m_picture;
    private WeakReference<ImageView> m_weakReference;

    DealImageLoader (Context context, ImageView imageView, String picture)
    {
        m_picture = picture;
        m_file = new File(context.getFilesDir(), "pic.png");
        m_weakReference = new WeakReference<>(imageView);
    }

    @Override
    protected File doInBackground (Void... params)
    {
        File file = m_file;
        byte[] byteFile = Base64.decode(m_picture,  Base64.NO_PADDING);

        BufferedOutputStream bos = null;
        try
        {
            bos = new BufferedOutputStream(new FileOutputStream(m_file));
            bos.write(byteFile);
        }
        catch (Exception e)
        {
            file = null;
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.flush();
                    bos.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    @Override
    protected void onPostExecute (File file)
    {
        ImageView imageView = m_weakReference.get();
        if (imageView != null)
        {
            Context context = imageView.getContext();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();

            int size = (int) (metrics.density * 55);

            Picasso.with(imageView.getContext())
                    .load(file)
                    .resize(size, size)
                    .onlyScaleDown()
                    .centerInside()
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }
}

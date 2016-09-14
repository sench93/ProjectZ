package manqaro.projectz.Cartoonizer.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.util.ArrayList;
import manqaro.projectz.Cartoonizer.CreateCartoon;
import manqaro.projectz.R;

/**
 * Created by Admin on 9/13/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<String> imageSet;
    private Context context;
    private ImageView mainImageView;
    private CreateCartoon cc ;


    public RecyclerAdapter(ArrayList<String> imageSet, Context context, ImageView mainImageView){
        this.imageSet = imageSet;
        this.context = context;
        this.mainImageView = mainImageView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mButton;
        public ViewHolder(View v) {
            super(v);
            this.mButton = (Button) v.findViewById(R.id.cardButton);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_animation_card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,  int position) {
        holder.mButton.setBackgroundColor(Color.parseColor(imageSet.get(position)));
        final ArrayList<Object> tempObjects = new ArrayList<>();
        tempObjects.add(context);
        tempObjects.add(mainImageView);
        tempObjects.add(position);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              mainImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.jobs));
                cc= new CreateCartoon(context);
                cc.execute(tempObjects);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageSet.size();
    }

}

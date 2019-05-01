package arpit.com.farmis.homerv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import arpit.com.farmis.R;

public class RVAdapter extends RecyclerView.Adapter<RVViewHolder>{

    private final Presenter presenter;
    Context context;



    public RVAdapter(Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        presenter.onBindListRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getListRowsCount();
    }

}

package si.uni_lj.fri.pbd.miniapp3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.models.dto.IngredientDTO;

public class SpinnerAdapter extends BaseAdapter {
    private List<IngredientDTO> ingredients;
    private Context context;
    private LayoutInflater inflater;

    public SpinnerAdapter(List<IngredientDTO> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(ingredients.get(position).getIdIngredient());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, null);

            TextView tvIngredient = convertView.findViewById(R.id.text_view_spinner_item);
            tvIngredient.setText(ingredients.get(position).getStrIngredient());
        }

        return convertView;
    }
}

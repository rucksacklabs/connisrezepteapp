package de.reneruck.connisRezepteApp;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RezepteListAdapter extends BaseAdapter implements Filterable {

	private List<Rezept> data;
	private ViewHolder holder;

	public RezepteListAdapter(List<Rezept> data) {
		this.data = data;
		this.holder = new ViewHolder();
	}

	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		// When convertView is not null, we can reuse it directly, there is
		// no need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.rezeptelist_entry, null);
			// Creates a ViewHolder and store references to the children views
			// we want to bind data to.
			this.holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			this.holder.toptext = (TextView) convertView.findViewById(R.id.entry_toptext);
			this.holder.subtext = (TextView) convertView.findViewById(R.id.subtext);

		}

		this.holder.toptext.setText(this.data.get(position).getName());
		this.holder.subtext.setText(this.data.get(position).getDocumentPath());

		((LinearLayout)convertView).setTag(this.data.get(position).getId());
		return convertView;
	}

	class ViewHolder {
		TextView toptext;
		TextView subtext;
		ImageView icon;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

}
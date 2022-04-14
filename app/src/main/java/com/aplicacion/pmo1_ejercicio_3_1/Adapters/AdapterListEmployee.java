package com.aplicacion.pmo1_ejercicio_3_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.aplicacion.pmo1_ejercicio_3_1.Models.Employee;
import com.aplicacion.pmo1_ejercicio_3_1.R;

import java.util.ArrayList;

public class AdapterListEmployee extends BaseAdapter {

    private Context context;
    private ArrayList<Employee> listItem;
    private ArrayList<Employee> filterlist;

    private CustomFilter filter;

    public AdapterListEmployee(Context context, ArrayList<Employee> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.filterlist = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Employee employee = (Employee) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.item_list_employee, null);

        TextView textViewName = (TextView) view.findViewById(R.id.itemName);
        TextView textViewJob = (TextView) view.findViewById(R.id.itemJob);

        textViewName.setText(employee.getNames() + " " + employee.getSurnames());
        textViewJob.setText(employee.getJob());

        return view;
    }

    //Filter
    /**********************************************************************************/

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.length()>0){

                charSequence = charSequence.toString().toUpperCase();

                ArrayList<Employee> filters = new ArrayList<>();

                for(int i = 0;i < filterlist.size(); i++){

                    if((filterlist.get(i).getNames() + filterlist.get(i).getSurnames()).toUpperCase().contains(charSequence)){

                        filters.add(filterlist.get(i));
                    }
                }

                filterResults.count = filters.size();
                filterResults.values = filters;

            }else {

                filterResults.count = filterlist.size();
                filterResults.values = filterlist;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            listItem = (ArrayList<Employee>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public Filter getFilter(){

        if(filter == null){
            filter = new CustomFilter();
        }

        return filter;
    }


}

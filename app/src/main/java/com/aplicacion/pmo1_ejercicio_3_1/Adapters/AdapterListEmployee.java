package com.aplicacion.pmo1_ejercicio_3_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aplicacion.pmo1_ejercicio_3_1.Models.Employee;
import com.aplicacion.pmo1_ejercicio_3_1.R;

import java.util.ArrayList;

public class AdapterListEmployee extends BaseAdapter {

    private Context context;
    private ArrayList<Employee> listItem;

    public AdapterListEmployee(Context context, ArrayList<Employee> listItem) {
        this.context = context;
        this.listItem = listItem;
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

    public Employee getEmployee(int i){
        return listItem.get(i);
    }
}

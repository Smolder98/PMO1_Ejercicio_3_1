package com.aplicacion.pmo1_ejercicio_3_1.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aplicacion.pmo1_ejercicio_3_1.Adapters.AdapterListEmployee;
import com.aplicacion.pmo1_ejercicio_3_1.Configuration.SQLiteConexion;
import com.aplicacion.pmo1_ejercicio_3_1.Configuration.TransactionDatabase;
import com.aplicacion.pmo1_ejercicio_3_1.Models.Employee;
import com.aplicacion.pmo1_ejercicio_3_1.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private View view;
    private ListView listView;

    private SQLiteConexion conexion;

    ArrayList<Employee> arrayListEmployee;

    AdapterListEmployee adapterListEmployee;

    public ListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        init();
        getEmployeeDatabase();

        return view;
    }

    private void init(){
        listView = (ListView) view.findViewById(R.id.listViewEmployees);

        conexion = new SQLiteConexion(getContext(), TransactionDatabase.NAME_DATABASE, null, 1);

        arrayListEmployee = new ArrayList<>();

        adapterListEmployee = new AdapterListEmployee(getContext(), arrayListEmployee);

        listView.setAdapter(adapterListEmployee);
    }

    private void getEmployeeDatabase(){
        SQLiteDatabase database = conexion.getReadableDatabase();

        Employee empTemp = null;

        Cursor cursor = database.rawQuery(TransactionDatabase.SELECT_ALL_TABLE_EMPLOYEE, null);

        while (cursor.moveToNext()){
            empTemp = new Employee();

            empTemp.setId(cursor.getLong(0));

            empTemp.setNames(cursor.getString(1));

            empTemp.setSurnames(cursor.getString(2));

            empTemp.setJob(cursor.getString(4));

            arrayListEmployee.add(empTemp);
        }
        cursor.close();

        adapterListEmployee.notifyDataSetChanged();
    }
}
package com.aplicacion.pmo1_ejercicio_3_1.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.aplicacion.pmo1_ejercicio_3_1.Adapters.AdapterListEmployee;
import com.aplicacion.pmo1_ejercicio_3_1.Configuration.SQLiteConexion;
import com.aplicacion.pmo1_ejercicio_3_1.Configuration.TransactionDatabase;
import com.aplicacion.pmo1_ejercicio_3_1.Models.Employee;
import com.aplicacion.pmo1_ejercicio_3_1.R;
import com.aplicacion.pmo1_ejercicio_3_1.Utils.MethodUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditFragment extends Fragment {
    private View view;
    
    private ConstraintLayout containerTwo;
    
    private TextView textViewSelection;
    
    private SQLiteConexion conexion;

    private ArrayList<Employee> arrayListEmployee;

    private AdapterListEmployee adapterListEmployee;

    private Employee employeeSelected;

    private AutoCompleteTextView menuJobs;

    private final String arrayItemMenu[] = {"Director Ejecutivo",
            "Director de Operaciones",
            "Director Comercial",
            "Director de Marketing",
            "Director de Recursos",
            "Director Financiero"};

    private ArrayAdapter adapterMenu;

    private TextInputEditText editTextName, editTextSurname, editTextAge, editTextDirection;

    private Button btnSave, btnDelete;

    public EditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        init();
        setListeners();

        return view;
    }

    private void init(){

        employeeSelected = null;

        conexion = new SQLiteConexion(getContext(), TransactionDatabase.NAME_DATABASE, null, 1);
        
        textViewSelection = (TextView) view.findViewById(R.id.textviewSelectEmployee);
        
        containerTwo = (ConstraintLayout) view.findViewById(R.id.containerTwo);

        menuJobs = (AutoCompleteTextView) view.findViewById(R.id.textfielMenu);
        adapterMenu = new ArrayAdapter(getContext(), R.layout.list_item, arrayItemMenu);
        menuJobs.setAdapter(adapterMenu);

        btnSave = (Button) view.findViewById(R.id.buttonSave);

        btnDelete = (Button) view.findViewById(R.id.buttonDelete);

        editTextName = (TextInputEditText) view.findViewById(R.id.textfielNames);

        editTextSurname = (TextInputEditText) view.findViewById(R.id.textfielSurnames);

        editTextAge = (TextInputEditText) view.findViewById(R.id.textfielAge);

        editTextDirection = (TextInputEditText) view.findViewById(R.id.textfielDirection);


    }

    private void setListeners(){
        textViewSelection.setOnClickListener(v -> dialogAddMember());

        btnSave.setOnClickListener(v -> editEmployee());

        btnDelete.setOnClickListener(v -> deleteEmployee());
    }

    private void editEmployee() {
        if(allowSave()){
            try {
                SQLiteConexion conexion = new SQLiteConexion(getContext(), TransactionDatabase.NAME_DATABASE, null, 1);
                SQLiteDatabase database = conexion.getWritableDatabase();

                ContentValues values = new ContentValues();

                String id = employeeSelected.getId()+"";
                String names = editTextName.getText().toString();
                String surnames = editTextSurname.getText().toString();
                String age = editTextAge.getText().toString();
                String direction = editTextDirection.getText().toString();
                String job = menuJobs.getText().toString();

                values.put(TransactionDatabase.EMPLOYEE_ID, id);
                values.put(TransactionDatabase.EMPLOYEE_NAMES, names);
                values.put(TransactionDatabase.EMPLOYEE_SURNAMES, surnames);
                values.put(TransactionDatabase.EMPLOYEE_AGE, age);
                values.put(TransactionDatabase.EMPLOYEE_DIRECTION, direction);
                values.put(TransactionDatabase.EMPLOYEE_JOB, job);

                Long result = database.replace(TransactionDatabase.NAME_TABLE, TransactionDatabase.EMPLOYEE_ID, values);

                if(result > 0){
                    MethodUtils.showMessageDialog("Mensaje", "El empleado a sido modificado exitosamente !!!", getContext());
                    getEmployeeDatabase();
                    //employeeSelected(false);
                }else {
                    MethodUtils.showMessageDialog("Error", "A ocurrido un error al modificar al empleado", getContext());
                }

            }catch (Exception e){
                MethodUtils.showMessageToast("Error: " + e.getMessage(), getContext());
            }
        }
    }

    private void deleteEmployee() {
        try {
            SQLiteConexion conexion = new SQLiteConexion(getContext(), TransactionDatabase.NAME_DATABASE, null, 1);
            SQLiteDatabase database = conexion.getWritableDatabase();

            int result = database.delete(TransactionDatabase.NAME_TABLE, TransactionDatabase.EMPLOYEE_ID+"=?",
                    new String[]{employeeSelected.getId()+""});


            if(result > 0){
                MethodUtils.showMessageDialog("Mensaje", "El empleado a sido eliminado exitosamente !!!", getContext());
                getEmployeeDatabase();
                employeeSelected(false);
            }else {
                MethodUtils.showMessageDialog("Error", "A ocurrido un error al eliminar el empleado", getContext());
            }

        }catch (Exception e){
            MethodUtils.showMessageToast("Error: " + e.getMessage(), getContext());
        }
    }

    private boolean allowSave(){
        String message = "";

        if(editTextName.getText().toString().isEmpty()) message = "Por favor ingrese los nombres del empleado";
        else if(editTextSurname.getText().toString().isEmpty()) message = "Por favor ingrese los apellidos del empleado";
        else if(editTextAge.getText().toString().isEmpty()) message = "Por favor ingrese la edad del empleado";
        else if(editTextDirection.getText().toString().isEmpty()) message = "Por favor ingrese la direccion del empleado";
        else if(menuJobs.getText().toString().isEmpty()) message = "Por favor selecciones una cargo para el empleado";
        else if(!MethodUtils.validateOnlyLetter(editTextName.getText().toString())) message = "Caracter no valido en el campo nombres";
        else if(!MethodUtils.validateOnlyLetter(editTextSurname.getText().toString())) message = "Caracter no valido en el campo apellidos";
        else if(!MethodUtils.validateNumberInteger(editTextAge.getText().toString())) message = "Caracter no valido en el campo edad";

        if(!message.isEmpty()){
            MethodUtils.showMessageDialog("Advertencia", message, getContext());
            return false;
        }else  return true;
    }



    private void dialogAddMember(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_select_employee, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        dialog.show();

        EditText text =(EditText) view.findViewById(R.id.textNameDialog);

        ListView listView = (ListView) view.findViewById(R.id.listViewDialog);

        arrayListEmployee = new ArrayList<>();

        adapterListEmployee = new AdapterListEmployee(getContext(), arrayListEmployee);

        listView.setAdapter(adapterListEmployee);

        getEmployeeDatabase();

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterListEmployee.getFilter().filter(charSequence);
                }catch (Exception e){}
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                employeeSelected = (Employee) adapterView.getAdapter().getItem(i);

                employeeSelected(true);

                dialog.dismiss();
            }
        });


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

            empTemp.setDirection(cursor.getString(3));

            empTemp.setJob(cursor.getString(4));

            empTemp.setAge(cursor.getInt(5));

            arrayListEmployee.add(empTemp);
        }
        cursor.close();

        adapterListEmployee.notifyDataSetChanged();
    }
    
    private void employeeSelected(boolean selected){
        
        if(selected){
            containerTwo.setVisibility(View.VISIBLE);
            fillInputs();
        }else {
            containerTwo.setVisibility(View.GONE);
            employeeSelected = null;
            clearInputs();
        }
    }

    private void fillInputs() {
        editTextName.setText(employeeSelected.getNames());
        editTextSurname.setText(employeeSelected.getSurnames());
        editTextAge.setText(employeeSelected.getAge()+"");
        editTextDirection.setText(employeeSelected.getDirection());

        menuJobs.setText(employeeSelected.getJob(), false);
    }

    private void clearInputs() {

        editTextName.setText(null);
        editTextSurname.setText(null);
        editTextAge.setText(null);
        editTextDirection.setText(null);

        menuJobs.setText(null);
    }
}
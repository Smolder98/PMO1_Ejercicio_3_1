package com.aplicacion.pmo1_ejercicio_3_1.Fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.aplicacion.pmo1_ejercicio_3_1.Configuration.SQLiteConexion;
import com.aplicacion.pmo1_ejercicio_3_1.Configuration.TransactionDatabase;
import com.aplicacion.pmo1_ejercicio_3_1.R;
import com.aplicacion.pmo1_ejercicio_3_1.Utils.MethodUtils;
import com.google.android.material.textfield.TextInputEditText;


public class AddFragment extends Fragment {


    private View view;

    private AutoCompleteTextView menuJobs;

    private final String arrayItemMenu[] = {"Director Ejecutivo",
                                            "Director de Operaciones",
                                            "Director Comercial",
                                            "Director de Marketing",
                                            "Director de Recursos",
                                            "Director Financiero"};

    private ArrayAdapter adapterMenu;

    private TextInputEditText editTextName, editTextSurname, editTextAge, editTextDirection;

    private Button btnSave;

    public AddFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);


        init();
        setListeners();

        return view;
    }

    private void init(){
        menuJobs = (AutoCompleteTextView) view.findViewById(R.id.textfielMenu);
        adapterMenu = new ArrayAdapter(getContext(), R.layout.list_item, arrayItemMenu);
        menuJobs.setAdapter(adapterMenu);

        btnSave = (Button) view.findViewById(R.id.buttonSave);

        editTextName = (TextInputEditText) view.findViewById(R.id.textfielNames);

        editTextSurname = (TextInputEditText) view.findViewById(R.id.textfielSurnames);

        editTextAge = (TextInputEditText) view.findViewById(R.id.textfielAge);

        editTextDirection = (TextInputEditText) view.findViewById(R.id.textfielDirection);

    }

    private void setListeners(){


        btnSave.setOnClickListener(v -> saveEmployee());



    }

    private void saveEmployee(){

        if(allowSave()){
            try {
                SQLiteConexion conexion = new SQLiteConexion(getContext(), TransactionDatabase.NAME_DATABASE, null, 1);
                SQLiteDatabase database = conexion.getWritableDatabase();

                ContentValues values = new ContentValues();

                String names = editTextName.getText().toString();
                String surnames = editTextSurname.getText().toString();
                String age = editTextAge.getText().toString();
                String direction = editTextDirection.getText().toString();
                String job = menuJobs.getText().toString();

                values.put(TransactionDatabase.EMPLOYEE_NAMES, names);
                values.put(TransactionDatabase.EMPLOYEE_SURNAMES, surnames);
                values.put(TransactionDatabase.EMPLOYEE_AGE, age);
                values.put(TransactionDatabase.EMPLOYEE_DIRECTION, direction);
                values.put(TransactionDatabase.EMPLOYEE_JOB, job);

                Long result = database.insert(TransactionDatabase.NAME_TABLE, TransactionDatabase.EMPLOYEE_ID, values);

                if(result > 0){
                    MethodUtils.showMessageDialog("Mensaje", "El empleado a sido guardado exitosamente !!!", getContext());

                    cleanInputs();
                }else {
                    MethodUtils.showMessageDialog("Error", "A ocurrido un error al ingresar al empleado", getContext());
                }

            }catch (Exception e){
                MethodUtils.showMessageToast("Error: " + e.getMessage(), getContext());
            }
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

    private void cleanInputs(){
        editTextName.setText(null);
        editTextSurname.setText(null);
        editTextAge.setText(null);
        editTextDirection.setText(null);

        menuJobs.setText(null);
    }
}
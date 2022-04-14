package com.aplicacion.pmo1_ejercicio_3_1.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

        }
    }

    private boolean allowSave(){
        String message = "";

        if(editTextName.getText().toString().isEmpty()) message = "Por favor ingrese los nombres del empleado";
        else if(editTextSurname.getText().toString().isEmpty()) message = "Por favor ingrese los apellidos del empleado";
        else if(editTextAge.getText().toString().isEmpty()) message = "Por favor ingrese la edad del empleado";
        else if(editTextDirection.getText().toString().isEmpty()) message = "Por favor ingrese la direccion del empleado";
        else if(!MethodUtils.validateOnlyLetter(editTextName.getText().toString())) message = "Caracter no valido en el campo nombres";
        else if(!MethodUtils.validateOnlyLetter(editTextSurname.getText().toString())) message = "Caracter no valido en el campo apellidos";
        else if(!MethodUtils.validateNumberInteger(editTextAge.getText().toString())) message = "Caracter no valido en el campo edad";

        if(!message.isEmpty()){
            MethodUtils.showMessageDialog("Advertencia", message, getContext());
            return false;
        }else  return true;
    }
}
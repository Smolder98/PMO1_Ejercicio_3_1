package com.aplicacion.pmo1_ejercicio_3_1.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodUtils {


    public static void showMessageToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void showMessageDialog(String title, String message, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message).setTitle(title);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static boolean validateNumberInteger(String number){
        try {
            Integer.parseInt(number);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean validateOnlyLetter(String text){
        Pattern pat = Pattern.compile("^[a-zA-ZáéíóúÁÉÓÚÍ ]+$");
        Matcher mat = pat.matcher(text.trim());
        return (mat.matches());
    }

}

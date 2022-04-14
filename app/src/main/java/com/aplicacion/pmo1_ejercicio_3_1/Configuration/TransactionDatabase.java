package com.aplicacion.pmo1_ejercicio_3_1.Configuration;

public class TransactionDatabase {
    // Nombre de la base de datos
    public static final String NAME_DATABASE = "DataBaseEmployee";

    //Nombre de la tabla en la base de datos
    public static final String NAME_TABLE = "employees";

    //Creacion de los atributos
    public static final String EMPLOYEE_ID = "id";
    public static final String EMPLOYEE_NAMES = "names";
    public static final String EMPLOYEE_SURNAMES = "surnames";
    public static final String EMPLOYEE_DIRECTION = "direction";
    public static final String EMPLOYEE_JOB = "job";
    public static final String EMPLOYEE_AGE = "age";

    //Creacion de la tabla
    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + NAME_TABLE +
            "("+
            EMPLOYEE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            EMPLOYEE_NAMES +" TEXT, "+
            EMPLOYEE_SURNAMES +" TEXT, "+
            EMPLOYEE_DIRECTION +" TEXT, "+
            EMPLOYEE_JOB +" TEXT, "+
            EMPLOYEE_AGE +" INTEGER"+
            ")";

    public static final String DROP_TABLE_EMPLOYEE = "DROP TABLE IF EXIST " + NAME_TABLE;
    public static final String SELECT_ALL_TABLE_EMPLOYEE = "SELECT * FROM " + NAME_TABLE;

}

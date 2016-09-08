package com.msig.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wmazariegos on 07/09/2016.
 */
public class consultasDataBase {
    Context context;
    public consultasDataBase(Context context) {
        this.context = context;
    }
    public String consulta(String tabla,String campos,String condicion){
        System.out.println("realizando consulta : select "+campos+" from "+tabla+" "+condicion+" ");
        String resultado = "";
        AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select "+campos+" from "+tabla+" "+condicion+" ",null);
        Integer numero_datos = fila.getCount();
        if (numero_datos > 0) {
            if (fila.moveToFirst()) {
                do {
                    if(numero_datos > 1){
                        resultado = resultado +"\n"+fila.getString(0);
                    }else{
                        resultado = fila.getString(0);
                    }
                    System.out.println("resulta: "+resultado);
                } while (fila.moveToNext());
            }
        }
        bd.close();
        return resultado;
    }
}

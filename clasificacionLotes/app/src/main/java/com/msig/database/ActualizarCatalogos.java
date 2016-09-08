package com.msig.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.widget.RelativeLayout;

/**
 * Created by wmazariegos on 31/08/2016.
 */
public class ActualizarCatalogos {
    Context context;
    RelativeLayout container;
    public ActualizarCatalogos(Context context,RelativeLayout container) {
        this.context = context;
        this.container = container;
        ponderacion_obstaculos();
        ponderacion_circuitotransporte();
        ponderacion_partidorespantes();
        ponderacion_trasiegoplano();
        ponderacion_tiposuelo();
        ponderacion_cantidadpiedra();
        Snackbar.make(container, "Actualización de Catálogos ha finalizado", Snackbar.LENGTH_LONG).show();
    }
    private void ponderacion_obstaculos(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Sin Obstaculos','100')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Rumas','90')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Quineles','85')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Rumas + Quineles','80')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Obra Gris','70')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Obra Gris + Quineles','50')");
            bd.execSQL("insert into ponderacion_obstaculos (nombre,ponderacion) values ('Obra Gris + Quineles + Rumas','20')");
            System.out.println("INSERT EJECUTADO ponderacion_obstaculos");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_obstaculos"+e);
        }
    }
    private void ponderacion_circuitotransporte(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_circuitotransporte (nombre,ponderacion) values ('Presenta Circuito','100')");
            bd.execSQL("insert into ponderacion_circuitotransporte (nombre,ponderacion) values ('Falta Rondas','75')");
            bd.execSQL("insert into ponderacion_circuitotransporte (nombre,ponderacion) values ('Mejorar Diseño','50')");
            bd.execSQL("insert into ponderacion_circuitotransporte (nombre,ponderacion) values ('No Presenta','0')");
            System.out.println("INSERT EJECUTADO ponderacion_circuitotransporte");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_circuitotransporte "+e);
        }
    }
    private void ponderacion_partidorespantes(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_partidorespantes (nombre,ponderacion) values ('Presenta','100')");
            bd.execSQL("insert into ponderacion_partidorespantes (nombre,ponderacion) values ('Deficiente','70')");
            bd.execSQL("insert into ponderacion_partidorespantes (nombre,ponderacion) values ('No Presenta','40')");
            System.out.println("INSERT EJECUTADO ponderacion_partidorespantes");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_partidorespantes "+e);
        }
    }
    private void ponderacion_trasiegoplano(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_trasiegoplano (nombre,ponderacion) values ('Si','100.00')");
            bd.execSQL("insert into ponderacion_trasiegoplano (nombre,ponderacion) values ('Mejorar','70.00')");
            bd.execSQL("insert into ponderacion_trasiegoplano (nombre,ponderacion) values ('Critico','-')");
            System.out.println("INSERT EJECUTADO ponderacion_trasiegoplano");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_trasiegoplano "+e);
        }
    }
    private void ponderacion_tiposuelo(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_tiposuelo (nombre,ponderacion) values ('Franco','100')");
            bd.execSQL("insert into ponderacion_tiposuelo (nombre,ponderacion) values ('Arcilloso','60')");
            bd.execSQL("insert into ponderacion_tiposuelo (nombre,ponderacion) values ('Arenoso','20')");
            System.out.println("INSERT EJECUTADO ponderacion_tiposuelo");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_tiposuelo "+e);
        }
    }
    private void ponderacion_cantidadpiedra(){
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('N/P','25')");
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('Leve','20.00')");
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('Moderado Dispersas','15.00')");
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('Moderado en Foco','10.00')");
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('Alto','5.00')");
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('En Exceso','-')");
            System.out.println("INSERT EJECUTADO ponderacion_cantidadpiedra");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA ponderacion_cantidadpiedra "+e);
        }
    }
}

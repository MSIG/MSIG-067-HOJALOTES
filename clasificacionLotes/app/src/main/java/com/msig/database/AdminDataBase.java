package com.msig.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wmazariegos on 30/08/2016.
 */
public class AdminDataBase extends SQLiteOpenHelper {

    public AdminDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creando tabla de Ponderación de Obstaculos
        db.execSQL("create table ponderacion_obstaculos (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla de Ponderación de Circuito de Transporte
        db.execSQL("create table ponderacion_circuitotransporte (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla de Ponderación de Partidores de Pantes
        db.execSQL("create table ponderacion_partidorespantes (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla de Trasiego Plano
        db.execSQL("create table ponderacion_trasiegoplano (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla de Tipo de Suelo
        db.execSQL("create table ponderacion_tiposuelo (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla de Cantidad de Piedra
        db.execSQL("create table ponderacion_cantidadpiedra (" +
                " nombre text primary key," +
                " ponderacion text"+
                ")"
        );
        //Creando tabla principal
        db.execSQL("create table formularios (" +
                " idform integer primary key autoincrement," +
                " finca text," +
                " lote text," +
                " finca_lote text," +
                " descfinca text," +
                " distsiembra text," +
                " tch text," +
                " longitudsurco text," +
                " tons_surco text," +
                " pond1 text," +
                " rondalados text," +
                " pond2 text," +
                " presenciaobstaculos text," +
                " pond3 text," +
                " circuitotransporte text," +
                " pond4 text," +
                " partidores_pantes text," +
                " pond5 text," +
                " pendiente text," +
                " pond6 text," +
                " trasiegoplano text," +
                " pond7 text," +
                " tiposuelo text," +
                " pond8 text," +
                " piedraslote text," +
                " pond9 text," +
                " desnivelsurco text," +
                " pond10 text," +
                " total1 text," +
                " classugerida text," +
                " nombresupervisor text," +
                " mecanizable text," +
                " ha text," +
                " total2 text" +
                ")"
        );
        //Creando tabla de Fincas
        db.execSQL("create table finca (" +
                " codigofinca text primary key," +
                " nombrefinca text"+
                ")"
        );
        //Creando tabla Ponderacion porcentaje
        db.execSQL("create table ponderacion_porcentaje (" +
                " codigo text primary key," +
                " valor text" +
                ")"
        );
        //Creando tabla Ponderacion clasificacion
        db.execSQL("create table ponderacion_clasificacion (" +
                " codigo text primary key," +
                " minimo text," +
                " maximo text," +
                " descripcion text," +
                " eficiencia text"+
                ")"
        );
        //Creando tabla Ponderacion Toneladas/Surco
        db.execSQL("create table ponderacion_tons_surco (" +
                " tons_surco text primary key," +
                " ponderacion text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

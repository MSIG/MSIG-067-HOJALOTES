package com.msig.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RelativeLayout;

import com.msig.services.HttpHandlerFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wmazariegos on 05/09/2016.
 */
public class tblPonderacionCantidadPiedra {
    Context context;
    RelativeLayout container;
    private String URL = "";
    private String function = "fn_obtener_ponderacion_cantidad_piedra";

    public tblPonderacionCantidadPiedra(Context context, RelativeLayout container, String server, String system) {
        System.out.println("SERVER "+ server);
        System.out.println("SYSTEM "+ system);
        URL = "http://www."+server+".com/"+system+"/services/execute.php";
        this.context = context;
        downloadData();
    }

    private void downloadData(){
        HttpHandlerFunction service = new HttpHandlerFunction();
        String response = service.post(URL, function,null);
        JSONArray jsonArray = null;
        try {
            System.out.println("RESPUESTA "+ response);
            jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String) jsonObject.get("codigo");
                String ponderacion = (String) jsonObject.get("valor");
                insertartabla(nombre,ponderacion);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertartabla(String nombre, String ponderacion){
        System.out.println("INSERTANDO nombre "+nombre+" y ponderacion "+ ponderacion);
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_cantidadpiedra (nombre,ponderacion) values ('"+nombre+"','"+ponderacion+"')");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA AL CARGAR PONDERACION CANTIDAD PIEDRA"+e);
        }
    }
}

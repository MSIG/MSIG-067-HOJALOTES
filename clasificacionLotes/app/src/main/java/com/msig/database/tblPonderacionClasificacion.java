package com.msig.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RelativeLayout;

import com.msig.services.HttpHandlerFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wmazariegos on 04/09/2016.
 */
public class tblPonderacionClasificacion {
    Context context;
    RelativeLayout container;
    private String URL = "";
    private String function = "fn_obtener_ponderacion_clasificacion";

    public tblPonderacionClasificacion(Context context, RelativeLayout container, String server, String system) {
        System.out.println("SERVER "+ server);
        System.out.println("SYSTEM "+ system);
        this.context = context;
        URL = "http://www."+server+".com/"+system+"/services/execute.php";
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
                String codigo = (String) jsonObject.get("codigo");
                String minimo = (String) jsonObject.get("minimo");
                String maximo = (String) jsonObject.get("maximo");
                String descripcion = (String) jsonObject.get("descripcion");
                String eficiencia = (String) jsonObject.get("eficiencia");
                insertartabla(codigo,minimo,maximo,descripcion,eficiencia);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertartabla(String codigo, String minimo,String maximo,String descripcion, String deficiencia){
        System.out.println("INSETANDO CODIGO "+codigo+" y MAXIMO "+ maximo);
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into ponderacion_clasificacion (codigo,minimo,maximo,descripcion,eficiencia) values ('"+codigo+"','"+minimo+"','"+maximo+"','"+descripcion+"','"+deficiencia+"')");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA AL CARGAR PONDERACION CLASIFICACION"+e);
        }
    }
}

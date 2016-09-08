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
public class tblFincas {
    Context context;
    RelativeLayout container;
    private String URL = "";
    private String function = "fn_obtener_finca";

    public tblFincas(Context context, RelativeLayout container, String server,String system) {
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
                String codigo = (String) jsonObject.get("c");
                String nombre = (String) jsonObject.get("n");
                insertartabla(codigo,nombre);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertartabla(String codigo, String nombre){
        System.out.println("INSERTANDO CODIGO "+codigo+" y nombre "+ nombre);
        try {
            AdminDataBase admin = new AdminDataBase(context,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            bd.execSQL("insert into finca (codigofinca,nombrefinca) values ('"+codigo+"','"+nombre+"')");
            bd.close();
        }catch (Exception e){
            System.out.println("ERROR EN CONSULTA AL CARGAR FINCA"+e);
        }
    }
}

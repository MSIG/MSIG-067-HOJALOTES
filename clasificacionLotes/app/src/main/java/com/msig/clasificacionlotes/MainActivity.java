package com.msig.clasificacionlotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msig.config.Servidor;
import com.msig.config.Sistema;
import com.msig.database.AdminDataBase;
import com.msig.database.tblFincas;
import com.msig.database.tblPonderacionCantidadPiedra;
import com.msig.database.tblPonderacionCircuitoTransporte;
import com.msig.database.tblPonderacionClasificacion;
import com.msig.database.tblPonderacionObstaculos;
import com.msig.database.tblPonderacionPartidoresPantes;
import com.msig.database.tblPonderacionPorcentaje;
import com.msig.database.tblPonderacionTipoSuelo;
import com.msig.database.tblPonderacionTrasiegoPlano;
import com.msig.database.tblPonderacion_tons_surco;
import com.msig.objetos.MaestroFormulario;
import com.msig.services.HttpHandlerFunction;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private boolean errorEnUnDato = false;
    private boolean errorEnAlgunEnvio = false;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.statusEnviando) TextView statusEnviando;
    @Bind(R.id.btn_indice_nuevoformulario) Button btn_nuevoformulario;
    @Bind(R.id.btn_indice_guardarservidor) Button btn_guardarservidor;
    @Bind(R.id.btn_indice_eliminarlocal) Button btn_eliminarlocal;
    @Bind(R.id.btn_indice_verdatos) Button btn_verdatos;
    @Bind(R.id.container) RelativeLayout container;
    private String URL = "";
    private String function = "fn_insertar_clasificacion";
    private Servidor sr = new Servidor(this);
    private Sistema si = new Sistema(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        URL = "http://www."+sr.getServer()+".com/"+si.getSistem()+"/services/execute.php";
        //Agregando funcionalidad para conección tradicional
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    @OnClick(R.id.btn_indice_nuevoformulario)
    public void nuevoForm(){
        Intent verForm;
        verForm = new Intent(this, ListadoFormularios.class);
        verForm.putExtra("operacion","llenar");
        startActivity(verForm);
    }
    @OnClick(R.id.btn_indice_guardarservidor)
    public void guardarServer(){
        confirmarEnviar();
    }
    /**
     * Confirma el envio de datos al servidor
     */
    public void confirmarEnviar(){
        //VERIFICAR LA CONEXION A INTERNET
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Importante");
        dlg.setMessage("Esta seguro de almacenar esta informacion? Los datos enviados se eliminan del dispositivo");
        dlg.setCancelable(true);
        dlg.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dlg, int id) {
                dlg.cancel();
            }
        });

        dlg.setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dlg, int id) {
                dlg.cancel();
                ejecucion();//Arranca el hilo de ejecución
            }
        });
        dlg.show();
    }
    /**
     * <------------------------------------------------------------------------------------------->
     *     HILO DE ENVIO DE DATOS
     * </------------------------------------------------------------------------------------------->
     * Metodo que envia los datos al servidor
     *
     */
    public void ejecucion(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        statusEnviando.setText("Espere un momento mientras los datos se envian al servidor central");
                        bloqueoBotones(false);
                    }
                });
                enviarServidorMaestro();
                progressBar.post(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        statusEnviando.setText("Espere un momento mientras los datos se envian al servidor central");
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);

                    }
                });
            }
        }).start();
    }
    /**
     * Consulta los datos almacenados en el móvil ---------------------------------MAESTRO
     */
    public void enviarServidorMaestro(){
        try {
            Thread.sleep(300);
            System.out.println("ENTRANDO AL PROCESO MAESTRO....");
            AdminDataBase admin = new AdminDataBase(this,"datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from formularios",null);
            Integer numero_datos = fila.getCount();
            if (numero_datos > 0) {
                if (fila.moveToFirst()) {
                    do {
                        errorEnUnDato = false;
                        MaestroFormulario obj;
                        DecimalFormat decimales = new DecimalFormat("0.00");
                        obj = new MaestroFormulario(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6)
                                ,fila.getString(7),fila.getString(8),fila.getString(9),fila.getString(10),fila.getString(11),fila.getString(12),fila.getString(13),fila.getString(14),fila.getString(15)
                                ,fila.getString(16),fila.getString(17),fila.getString(18),fila.getString(19),fila.getString(20),fila.getString(21),fila.getString(22),fila.getString(23),fila.getString(24)
                                ,fila.getString(25),fila.getString(26),fila.getString(27),fila.getString(28),fila.getString(29),fila.getString(30),fila.getString(31),fila.getString(32),fila.getString(33));
                        enviar(obj);
                        if(errorEnUnDato){
                            errorEnAlgunEnvio = true;
                            System.out.println("HA OCURRIDO UN ERROR EN ALGUN DATO DEL DETALLE");
                        }else{
                            eliminarEnviado(Integer.parseInt(obj.getId()));
                        }
                    } while (fila.moveToNext());
                    if(errorEnAlgunEnvio){
                        Snackbar.make(container, "Algunos datos no se enviaron, itentente nuevamente", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(container, "Todos los datos se enviaron exitosamente", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
            bd.close();
        } catch(InterruptedException e) {}
    }
    public void eliminarEnviado(int id_formulario) {
        try {
            AdminDataBase admin = new AdminDataBase(this, "datab", null, 1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            bd.execSQL("delete from formularios where idform = "+id_formulario+"");
            bd.close();
            System.out.println("FIN DE TODO EL PROCESO");
        } catch (Exception e) {
            System.out.println("ERROR AL ELIMINAR ENVIADO: "+ e);
        }
    }
    /**
     * Envia los datos al servidor -----------------------------------------MAESTRO
     * @param objEnviar
     */
    public void enviar(MaestroFormulario objEnviar) {
        try {
            Thread.sleep(300);
            System.out.println("COMENZANDO ENVIO DE MAESTRO ....");
            String values = ""+objEnviar.getId()+"," +
                    ""+objEnviar.getFinca()+"," +
                    ""+objEnviar.getLote()+"," +
                    ""+objEnviar.getFinca_lote()+"," +
                    "'"+objEnviar.getDescfinca()+"'," +
                    ""+objEnviar.getDistsiembra()+"," +
                    ""+objEnviar.getTch()+"," +
                    ""+objEnviar.getLongitudsurco()+","+
                    ""+objEnviar.getTons_surco()+"," +
                    ""+objEnviar.getPond1()+"," +
                    ""+objEnviar.getRondalados()+"," +
                    ""+objEnviar.getPond2()+"," +
                    "'"+objEnviar.getPresenciaobstaculos()+"'," +
                    ""+objEnviar.getPond3()+"," +
                    "'"+objEnviar.getCircuitotransporte()+"'," +
                    ""+objEnviar.getPond4()+"," +
                    "'"+objEnviar.getPartidores_pantes()+"'," +
                    ""+objEnviar.getPond5()+"," +
                    ""+objEnviar.getPendiente()+"," +
                    ""+objEnviar.getPond6()+"," +
                    "'"+objEnviar.getTrasiegoplano()+"'," +
                    ""+objEnviar.getPond7()+"," +
                    "'"+objEnviar.getTiposuelo()+"'," +
                    ""+objEnviar.getPond8()+"," +
                    "'"+objEnviar.getPiedraslote()+"'," +
                    ""+objEnviar.getPond9()+"," +
                    ""+objEnviar.getDesnivelsurco()+"," +
                    ""+objEnviar.getPond10()+"," +
                    ""+objEnviar.getTotal1()+"," +
                    "'"+objEnviar.getClassugerida()+"'," +
                    "'"+objEnviar.getNombresupervisor()+"'," +
                    ""+objEnviar.getMecanizable()+"," +
                    ""+objEnviar.getHa()+"," +
                    ""+objEnviar.getTotal2()+"," +
                    "'2016/09/04'";
            System.out.println("<--------------------QUERY SQL "+ values);
            try {
                HttpHandlerFunction service = new HttpHandlerFunction();
                String response = service.post(URL, function, values);
                String respuesta_servidor = response.trim();
                System.out.println("RESPUESTA SERVIDOR "+respuesta_servidor);
                if (respuesta_servidor.equals("OK")) {
                    System.out.println(" MAESTRO ENVIADO ");
                } else {
                    errorEnUnDato = true;
                    System.out.println(" ERROR AL ENVIAR MAESTRO "+ respuesta_servidor);
                }
            } catch (Exception error) {
                System.out.println("ERROR MAESTRO :"+error);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.btn_indice_verdatos)
    public void verDatos(){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_config) {
            Intent usuario_nuevo;
            usuario_nuevo = new Intent(this, ConfigActivity.class);
            startActivity(usuario_nuevo);
        }else{
            if(id == R.id.menu_master_obstaculos){
                PonderacionObstaculos();
            }else{
                if(id == R.id.menu_master_circuito){
                    PonderacionCircuitoTransporte();
                }else{
                    if(id == R.id.menu_master_partidores){
                        PonderacionPartidoresPantes();
                    }else{
                        if(id == R.id.menu_master_trasiego){
                            PonderacionTrasiegoPlano();
                        }else{
                            if(id == R.id.menu_master_tiposuelo){
                                PonderacionTipoSuelo();
                            }else{
                                if(id == R.id.menu_master_cantidadpiedra){
                                    PonderacionCantidadPiedra();
                                }else{
                                    if(id == R.id.menu_master_porcentaje){
                                        PonderacionPorcentaje();
                                    }else{
                                        if(id == R.id.menu_master_clasificacion){
                                            PonderacionClasificacion();
                                        }else{
                                            if(id == R.id.menu_master_finca){
                                                Finca();
                                            }else{
                                                if(id == R.id.menu_master_ponderacion_tons_surco){
                                                    PonderacionTons_Surco();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Metodo para eliminar datos locales
     */
    @OnClick(R.id.btn_indice_eliminarlocal)
    public void eliminarLocal(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle("Importante");
        dlg.setMessage("Eliminar la base de datos local? ");
        dlg.setCancelable(true);
        dlg.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dlg, int id) {
                dlg.cancel();
            }
        });

        dlg.setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dlg, int id) {
                if(eliminarDatosLocal()){
                    Snackbar.make(container, "Datos eliminados con exito", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dlg.show();
    }
    /**
     * Elimina todos los datos de la base de datos
     * @return
     */
    public boolean eliminarDatosLocal(){
        try {
            AdminDataBase admin = new AdminDataBase(this, "datab", null, 1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            int cantidad = bd.delete("formularios", null,null);
            bd.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void Finca(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblFincas actualizar = new tblFincas(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionCantidadPiedra(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionCantidadPiedra actualizar = new tblPonderacionCantidadPiedra(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionTons_Surco(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacion_tons_surco actualizar = new tblPonderacion_tons_surco(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionCircuitoTransporte(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionCircuitoTransporte actualizar = new tblPonderacionCircuitoTransporte(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionClasificacion(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionClasificacion actualizar = new tblPonderacionClasificacion(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionObstaculos(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionObstaculos actualizar = new tblPonderacionObstaculos(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionPartidoresPantes(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionPartidoresPantes actualizar = new tblPonderacionPartidoresPantes(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionPorcentaje(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionPorcentaje actualizar = new tblPonderacionPorcentaje(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionTipoSuelo(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionTipoSuelo actualizar = new tblPonderacionTipoSuelo(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    public void PonderacionTrasiegoPlano(){
        new Thread(new Runnable() {
            public void run() {
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                tblPonderacionTrasiegoPlano actualizar = new tblPonderacionTrasiegoPlano(getBaseContext(),container,sr.getServer(),si.getSistem());
                progressBar.post(new Runnable() {
                    public void run() {
                        statusEnviando.setText("Espere un momento \n Actualizando Catálogos");
                        progressBar.setVisibility(View.VISIBLE);
                        statusEnviando.setVisibility(View.VISIBLE);
                        bloqueoBotones(false);
                    }
                });

                runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        statusEnviando.setVisibility(View.GONE);
                        bloqueoBotones(true);
                    }
                });
            }
        }).start();
    }
    /**
     * Bloque o desbloqueo de botones
     * @param estado
     */
    public void bloqueoBotones(boolean estado){
        btn_eliminarlocal.setEnabled(estado);
        btn_guardarservidor.setEnabled(estado);
        btn_nuevoformulario.setEnabled(estado);
        btn_verdatos.setEnabled(estado);
    }
}

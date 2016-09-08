package com.msig.Formularios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.msig.Interfaces.FormularioListener;
import com.msig.adaptador.AdapterFormularioCompleto;
import com.msig.clasificacionlotes.R;
import com.msig.database.AdminDataBase;
import com.msig.database.consultasDataBase;
import com.msig.objetos.MaestroFormulario;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClasificacionLotes extends AppCompatActivity implements AdapterView.OnItemClickListener, FormularioListener {

    private int id_form;
    private String modo;
    private List<String> listaobstaculos = new ArrayList<>();
    private List<String> listacircuitotransporte = new ArrayList<>();
    private List<String> listapartidorespantes = new ArrayList<>();
    private List<String> listatrasiegoplan = new ArrayList<>();
    private List<String> listatiposuelo = new ArrayList<>();
    private List<String> listapiedraslote = new ArrayList<>();
    @Bind(R.id.pantallaformularios) LinearLayout pantallaformularios;
    @Bind(R.id.btn_form_adddetalle) Button btn_detalle;
    @Bind(R.id.btn_form_actualizar) Button btn_actualizar;
    @Bind(R.id.txt_maestro_finca) EditText txt_finca;
    @Bind(R.id.txt_maestro_lote) EditText txt_lote;
    @Bind(R.id.txt_maestro_dist_siembra) EditText txt_dist_siembra;
    @Bind(R.id.txt_maestro_tch) EditText txt_tch;
    @Bind(R.id.txt_maestro_longitudsurco) EditText txt_longitudsurco;
    @Bind(R.id.txt_maestro_ronda4lados) EditText txt_ronda4lados;
    @Bind(R.id.spn_obstaculos) Spinner spn_obstaculos;
    @Bind(R.id.spn_circuitotransporte) Spinner spn_circuitotransporte;
    @Bind(R.id.spn_partidores_pantes) Spinner spn_partidores_pantes;
    @Bind(R.id.txt_maestro_porc_pendiente) EditText txt_porc_pendiente;
    @Bind(R.id.spn_trasiegoplano) Spinner spn_trasiegoplano;
    @Bind(R.id.spn_tipo_suelo) Spinner spn_tipo_suelo;
    @Bind(R.id.spn_piedras_lote) Spinner spn_piedras_lote;
    @Bind(R.id.txt_maestro_desnivel) EditText txt_desnivel;
    @Bind(R.id.txt_maestro_nombresupervisor) EditText txt_nombresupervisor;
    @Bind(R.id.txt_maestro_mecanizable) EditText txt_mecanizable;
    @Bind(R.id.txt_maestro_ha) EditText txt_ha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion_lotes);
        ButterKnife.bind(this);
        cargarSpinners("select nombre from ponderacion_obstaculos",listaobstaculos,"#Presencia de obstaculos");
        cargarSpinners("select nombre from ponderacion_circuitotransporte",listacircuitotransporte,"Circuito transporte");
        cargarSpinners("select nombre from ponderacion_partidorespantes",listapartidorespantes,"Partidores/pantes");
        cargarSpinners("select nombre from ponderacion_trasiegoplano",listatrasiegoplan,"Trasiego plano");
        cargarSpinners("select nombre from ponderacion_tiposuelo",listatiposuelo,"Tipo de suelo");
        cargarSpinners("select nombre from ponderacion_cantidadpiedra",listapiedraslote,"#Piedras/lote");
        id_form = getIntent().getExtras().getInt("id_form");
        if(id_form == 0){
            modo="insert";
            btn_actualizar.setVisibility(View.GONE);
        }else{
            modo="update";
        }
        actualizarLista();
        imprimircampos();
        final ArrayAdapter<String> adaptadorObstaculos = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaobstaculos){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorObstaculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_obstaculos.setAdapter(adaptadorObstaculos);
        spn_obstaculos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0){
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adaptadorCircuitoTransporte = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listacircuitotransporte){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorCircuitoTransporte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_circuitotransporte.setAdapter(adaptadorCircuitoTransporte);
        spn_circuitotransporte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0){
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adaptadorPartidoresPantes = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listapartidorespantes){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorPartidoresPantes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_partidores_pantes.setAdapter(adaptadorPartidoresPantes);
        spn_partidores_pantes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0) {
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adaptadorTrasiegoPlan = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listatrasiegoplan){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorTrasiegoPlan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_trasiegoplano.setAdapter(adaptadorTrasiegoPlan);
        spn_trasiegoplano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0) {
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adaptadorTipoSuelo = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listatiposuelo){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorTipoSuelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_tipo_suelo.setAdapter(adaptadorTipoSuelo);
        spn_tipo_suelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0) {
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        ArrayAdapter<String> adaptadorPiedrasLote = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listapiedraslote){
            @Override
            public boolean isEnabled(int position){
                if(position == 0){
                    return false;
                }else{
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    textView.setTextColor(Color.parseColor("#4caf50"));
                }else{
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorPiedrasLote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_piedras_lote.setAdapter(adaptadorPiedrasLote);
        spn_piedras_lote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(arg2 != 0) {
                    String tipo_seleccionado = arg0.getItemAtPosition(arg2).toString();
                    Toast.makeText(arg0.getContext(),
                            "Seleccionado: " + tipo_seleccionado,
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void imprimircampos() {
        consultasDataBase c = new consultasDataBase(this);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("------------------------------DATA---------------------------------");
        System.out.println("VALORES DE ponderacion_obstaculos \n" + c.consulta("ponderacion_obstaculos","ponderacion",""));
        System.out.println("VALORES DE ponderacion_circuitotransporte \n" + c.consulta("ponderacion_circuitotransporte","ponderacion",""));
        System.out.println("VALORES DE ponderacion_partidorespantes \n" + c.consulta("ponderacion_partidorespantes","ponderacion",""));
        System.out.println("VALORES DE ponderacion_trasiegoplano \n" + c.consulta("ponderacion_trasiegoplano","ponderacion",""));
        System.out.println("VALORES DE ponderacion_tiposuelo \n" + c.consulta("ponderacion_tiposuelo","ponderacion",""));
        System.out.println("VALORES DE ponderacion_cantidadpiedra \n" + c.consulta("ponderacion_cantidadpiedra","ponderacion",""));
        System.out.println("VALORES DE ponderacion_porcentaje \n" + c.consulta("ponderacion_porcentaje","valor",""));
        System.out.println("VALORES DE ponderacion_clasificacion \n" + c.consulta("ponderacion_clasificacion","codigo",""));
        System.out.println("VALORES DE ponderacion_tons_surco \n" + c.consulta("ponderacion_tons_surco","ponderacion",""));
        System.out.println("------------------------------DATA---------------------------------");
        System.out.println("-------------------------------------------------------------------");
    }

    private void cargarSpinners(String query,List<String> lista,String titulo){
        lista.add(titulo);
        AdminDataBase admin = new AdminDataBase(this,"datab", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(query,null);
        Integer numero_datos = fila.getCount();
        if (numero_datos > 0) {
            if (fila.moveToFirst()) {
                do {
                    String nombre = fila.getString(0);
                    lista.add(nombre);
                } while (fila.moveToNext());
            }
        }
        try {
            fila.close();
        }catch (Exception e){
            System.out.println("ERROR AL CERRAR CURSOR "+e);
        }
    }
    @OnClick(R.id.btn_form_adddetalle)
    public void agregarDetalle(){
        if(modo.equals("insert")){
            actualizarLista();
            if(validar()){
                if(guardarLocal()) {
                    Snackbar.make(pantallaformularios, "Registro exitoso", Snackbar.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Ha ocurrido un error al guardar los datos",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Asegurese de completar todos los campos",Toast.LENGTH_SHORT).show();
            }
        }
        actualizarLista();
    }
    @OnClick(R.id.btn_form_actualizar)
    public void actualizar(){

    }
    private boolean guardarLocal(){
        try{
            DecimalFormat format = new DecimalFormat("############.##");
            DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            format.setDecimalFormatSymbols(dfs);

            consultasDataBase c = new consultasDataBase(this);

            AdminDataBase admin = new AdminDataBase(this, "datab", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("finca", txt_finca.getText().toString());
            registro.put("lote", txt_lote.getText().toString());
            registro.put("finca_lote", txt_finca.getText().toString()+""+txt_lote.getText().toString());
            registro.put("descfinca", c.consulta("finca","nombrefinca","where codigofinca = '"+txt_finca.getText().toString()+"'"));
            registro.put("distsiembra", txt_dist_siembra.getText().toString());
            registro.put("tch", txt_tch.getText().toString());
            registro.put("longitudsurco", txt_longitudsurco.getText().toString());
            //txt_maestro_tct/(10000/txt_maestro_dist_siembra)*txt_maestro_longitudsurco
            String tons_surco = format.format(Float.parseFloat(txt_tch.getText().toString())/(10000/Float.parseFloat(txt_dist_siembra.getText().toString()))*Float.parseFloat(txt_longitudsurco.getText().toString()));
            registro.put("tons_surco", tons_surco);
            //(funcionPond1())* (select valor from ponderacion_porcentaje where codigo = 1)
            String pond1 = format.format(funcionPond1(tons_surco)*Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '1'")));
            registro.put("pond1",pond1);
            //(funcion())* (select valor from ponderacion_porcentaje where codigo = 2)
            registro.put("rondalados",txt_ronda4lados.getText().toString());
            String pond2 = format.format(funcionPond2()*Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '2'")));
            registro.put("pond2",pond2);
            registro.put("presenciaobstaculos",spn_obstaculos.getSelectedItem().toString());
            String pond3 = format.format((Float.parseFloat(c.consulta("ponderacion_obstaculos","ponderacion","where nombre = '"+spn_obstaculos.getSelectedItem().toString()+"'")))*
                                          Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '3'")));
            //(select ponderacion from ponderacion_obstaculos where nombre = spn_obstaculos)
            // * (select valor from ponderacion_porcentaje where codigo = 3)
            registro.put("pond3",pond3);
            registro.put("circuitotransporte",spn_circuitotransporte.getSelectedItem().toString());
            //(select ponderacion from ponderacion_circuitotransporte where nombre = spn_circuitotransporte)*
            // (select valor from ponderacion_porcentaje where codigo = 4)
            String pond4 = format.format((Float.parseFloat(c.consulta("ponderacion_circuitotransporte","ponderacion","where nombre = '"+spn_circuitotransporte.getSelectedItem().toString()+"'")))
                    *(Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '4'"))));
            registro.put("pond4",pond4);
            registro.put("partidores_pantes",spn_partidores_pantes.getSelectedItem().toString());
            String pond5 = format.format((Float.parseFloat(c.consulta("ponderacion_partidorespantes","ponderacion","where nombre = '"+spn_partidores_pantes.getSelectedItem().toString()+"'")))*
                    (Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '5'"))));
            //(select ponderacion from ponderacion_partidorespantes where nombre =  spn_partidores_pantes)*
            // (select valor from ponderacion_porcentaje where codigo = 5)
            registro.put("pond5",pond5);
            registro.put("pendiente",txt_porc_pendiente.getText().toString());
            String pond6 = format.format(funcionPond6()*Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '6'")));
            //(funcion())*(select valor from ponderacion_porcentaje where codigo = 6)
            registro.put("pond6",pond6);
            registro.put("trasiegoplano",spn_trasiegoplano.getSelectedItem().toString());
            //(select ponderacion from ponderacion_trasiegoplano where  nombre = spn_trasiegoplano)*
            //(select valor from ponderacion_porcentaje where codigo = 7)
            String pond7 = format.format(Float.parseFloat(c.consulta("ponderacion_trasiegoplano","ponderacion","where nombre = '"+spn_trasiegoplano.getSelectedItem().toString()+"'"))*
                    (Float.parseFloat(c.consulta("ponderacion_porcentaje","valor","where codigo = '7'"))));
            registro.put("pond7",pond7);
            registro.put("tiposuelo",spn_tipo_suelo.getSelectedItem().toString());
            //(select ponderacion from ponderacion_tiposuelo where nombre = spn_tipo_suelo)*
            // (select valor from ponderacion_porcentaje where codigo = 8)
            String pond8 = format.format((Float.parseFloat(c.consulta("ponderacion_tiposuelo","ponderacion","where nombre = '"+spn_tipo_suelo.getSelectedItem().toString()+"'")))*
                    (Float.parseFloat((c.consulta("ponderacion_porcentaje","valor","where codigo = '8'")))));
            registro.put("pond8",pond8);
            registro.put("piedraslote",spn_piedras_lote.getSelectedItem().toString());
            registro.put("pond9","0");
            registro.put("desnivelsurco","0");
            registro.put("pond10","0");
            registro.put("total1","0");
            registro.put("classugerida","0");
            registro.put("nombresupervisor","0");
            registro.put("mecanizable","0");
            registro.put("ha","0");
            registro.put("total2","0");


            bd.insert("formularios", null, registro);
            bd.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private Float funcionPond6(){
        float resultado = 0;
        //AI3+(AH3-AF3)/(AG3-AF3)*(AJ3-AI3)
        float AF3=0;
        float AG3=4;
        //txt_maestro_porc_pendiente
        float AH3 = Float.parseFloat(txt_porc_pendiente.getText().toString());
        float AI3= 100;
        float AJ3 = 25;
        resultado = AI3+(AH3-AF3)/(AG3-AF3)*(AJ3-AI3);
        return resultado;
    }
    private Float funcionPond1(String tons_surco){
        float result = 0;
        int H3 = 2;
        int I3 = 15;
        float J3 = Float.parseFloat(tons_surco);
        consultasDataBase c = new consultasDataBase(this);
        //select ponderacion from ponderacion_tons_surco where tons_surco = H3
        float K3 = Float.parseFloat(c.consulta("ponderacion_tons_surco","ponderacion","where tons_surco = '"+H3+"'"));
        //select ponderacion from ponderacion_tons_surco where tons_surco = I3
        float L3 = Float.parseFloat(c.consulta("ponderacion_tons_surco","ponderacion","where tons_surco = '"+I3+"'"));
        float M3= K3+(J3-H3)/(I3-H3)*(L3-K3);
        if( M3 >= 100){
            result = 100;
        }else{
            //Tabla sin datos ---- se setea 90 para prueba--------------------------------------------------------------x
            result = M3;
        }
        return result;
    }
    private Float funcionPond2(){
        float P3 = 1;
        float Q3 = 4;
        float R3 = Float.parseFloat(txt_ronda4lados.getText().toString());
        float S3 = 25;
        float T3 = 100;
        float result = S3+(R3-P3)/(Q3-P3)*(T3-S3);
        return result;
    }
    /**
     * Actualiza la lista del formulario activo
     */
    public void actualizarLista(){
        ArrayList<MaestroFormulario> category = consulta();
        ListView lv = (ListView) findViewById(R.id.list_detalle);
        AdapterFormularioCompleto adapter = new AdapterFormularioCompleto(this, category);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }
    /**
     * Recarga una lista con los datos del detalle
     * @return
     */
    public ArrayList<MaestroFormulario> consulta(){
        ArrayList<MaestroFormulario> category = new ArrayList<>();
        AdminDataBase admin = new AdminDataBase(this,"datab", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from formularios",null);
        Integer numero_datos = fila.getCount();
        if (numero_datos > 0) {
            if (fila.moveToFirst()) {
                do {
                    MaestroFormulario obj;
                    DecimalFormat decimales = new DecimalFormat("0.00");
                    obj = new MaestroFormulario(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6)
                            ,fila.getString(7),fila.getString(8),fila.getString(9),fila.getString(10),fila.getString(11),fila.getString(12),fila.getString(13),fila.getString(14),fila.getString(15)
                            ,fila.getString(16),fila.getString(17),fila.getString(18),fila.getString(19),fila.getString(20),fila.getString(21),fila.getString(22),fila.getString(23),fila.getString(24)
                            ,fila.getString(25),fila.getString(26),fila.getString(27),fila.getString(28),fila.getString(29),fila.getString(30),fila.getString(31),fila.getString(32),fila.getString(33));
                    category.add(obj);
                } while (fila.moveToNext());
            }
        }
        return category;
    }
    /**
     * Metodo que carga los datos del item seleccionado y los envia al Dialogo
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*try{
            TextView id_form = (TextView) view.findViewById(R.id.txt_detalle_id_form);
            String form_select = id_form.getText().toString();

            TextView estacion =(TextView) view.findViewById(R.id.txt_detalle_estacion);
            String estacion_select = estacion.getText().toString();

            TextView vivos = (TextView) view.findViewById(R.id.txt_detalle_vivos);
            String vivos_select = vivos.getText().toString();

            TextView ninfa1 =(TextView) view.findViewById(R.id.txt_detalle_ninfa1);
            String ninfa1_select = ninfa1.getText().toString();

            TextView ninfa2 = (TextView) view.findViewById(R.id.txt_detalle_ninfa2);
            String ninfa2_select = ninfa2.getText().toString();

            TextView tallos = (TextView) view.findViewById(R.id.txt_detalle_tallos);
            String tallos_select = tallos.getText().toString();

            AddDetalleChincheSalivosa frag = new AddDetalleChincheSalivosa();
            Bundle args = new Bundle();
            args.putInt("id_formulario",Integer.parseInt(form_select));
            args.putString("fecha",txtFecha_muestreo.getText().toString());
            args.putString("no_ficha",txtNo_ficha.getText().toString());
            args.putInt("estacion",Integer.parseInt(estacion_select));
            args.putInt("vivos",Integer.parseInt(vivos_select));
            args.putInt("ninfa1",Integer.parseInt(ninfa1_select));
            args.putInt("ninfa2",Integer.parseInt(ninfa2_select));
            args.putInt("tallos",Integer.parseInt(tallos_select));
            frag.setArguments(args);
            frag.show(getSupportFragmentManager(), "");
        }catch (Exception e){
            System.out.println("ERROR: "+ e);
        }
*/
    }
    /**
     * Valida que los campos no esten vacios
     * @return
     */
    public boolean validar(){
        if(!txt_finca.getText().toString().equals("") &&
                !txt_lote.getText().toString().equals("") &&
                !txt_dist_siembra.getText().toString().equals("") &&
                !txt_tch.getText().toString().equals("") &&
                !txt_longitudsurco.getText().toString().equals("") &&
                !txt_ronda4lados.getText().toString().equals("") &&
                !txt_porc_pendiente.getText().toString().equals("") &&
                !txt_desnivel.getText().toString().equals("") &&
                !txt_nombresupervisor.getText().toString().equals("") &&
                !txt_mecanizable.getText().toString().equals("") &&
                !txt_ha.getText().toString().equals("")){
            /*
            txtCargo_resp.setEnabled(false);
            txtFecha_muestreo.setEnabled(false);
            txtNo_ficha.setEnabled(false);
            txtNombre_resp.setEnabled(false);
            txtNumero_finca.setEnabled(false);
            txtNumero_muestra.setEnabled(false);
            txtArea.setEnabled(false);
            txtLote.setEnabled(false);
            */
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void pulsado() {
        actualizarLista();
    }
}

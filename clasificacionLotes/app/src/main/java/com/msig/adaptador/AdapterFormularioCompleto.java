package com.msig.adaptador;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.msig.clasificacionlotes.R;
import com.msig.objetos.MaestroFormulario;

import java.util.ArrayList;

/**
 * Created by wmazariegos on 02/09/2016.
 */
public class AdapterFormularioCompleto extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<MaestroFormulario> items;

    public AdapterFormularioCompleto(Activity activity, ArrayList<MaestroFormulario> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<MaestroFormulario> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_adapter_formulario, null);
        }

        MaestroFormulario dir = items.get(position);

        TextView id = (TextView) v.findViewById(R.id.id);
        id.setText("Id: "+dir.getId());

        TextView finca = (TextView) v.findViewById(R.id.finca);
        finca.setText("Finca: "+dir.getFinca());

        TextView lote = (TextView) v.findViewById(R.id.lote);
        lote.setText("Lote: "+dir.getLote());

        TextView finca_lote = (TextView) v.findViewById(R.id.fincalote);
        finca_lote.setText("Finca/Lote: "+dir.getFinca_lote());

        TextView descfinca = (TextView) v.findViewById(R.id.descfinca);
        descfinca.setText("Desc. Finca: "+dir.getDescfinca());

        TextView distsiembra = (TextView) v.findViewById(R.id.distsiembra);
        distsiembra.setText("Dist. Siembra: "+dir.getDistsiembra());

        TextView tch = (TextView) v.findViewById(R.id.tch);
        tch.setText("TCH: "+dir.getTch());

        TextView longitudsurco = (TextView) v.findViewById(R.id.longitudsurco);
        longitudsurco.setText("Long. Surco: "+dir.getLongitudsurco());

        TextView tons_surco = (TextView) v.findViewById(R.id.tonssurco);
        tons_surco.setText("Tons/Surco: "+dir.getTons_surco());

        TextView pond1 = (TextView) v.findViewById(R.id.pond1);
        pond1.setText("Pond1: "+dir.getPond1());

        TextView rondalados = (TextView) v.findViewById(R.id.ronda4lados);
        rondalados.setText("Ronda 4 Lados: "+dir.getRondalados());

        TextView pond2 = (TextView) v.findViewById(R.id.pond2);
        pond2.setText("Pond2: "+dir.getPond2());

        TextView presenciaobstaculos = (TextView) v.findViewById(R.id.presenciaobstaculos);
        presenciaobstaculos.setText("Presencia Obstáculos: "+dir.getPresenciaobstaculos());

        TextView pond3 = (TextView) v.findViewById(R.id.pond3);
        pond3.setText("Pond3: "+dir.getPond3());

        TextView circuitotransporte = (TextView) v.findViewById(R.id.circuitotransporte);
        circuitotransporte.setText("Circuito Trans.: "+dir.getCircuitotransporte());

        TextView pond4 = (TextView) v.findViewById(R.id.pond4);
        pond4.setText("Pond4: "+dir.getPond4());

        TextView partidorespantes = (TextView) v.findViewById(R.id.partidorespantes);
        partidorespantes.setText("Partidores/Pantes: "+dir.getPartidores_pantes());

        TextView pond5 = (TextView) v.findViewById(R.id.pond5);
        pond5.setText("Pond5: "+dir.getPond5());

        TextView pendiente = (TextView) v.findViewById(R.id.pendiente);
        pendiente.setText("%Pendiente:"+dir.getPendiente());

        TextView pond6 = (TextView) v.findViewById(R.id.pond6);
        pond6.setText("Pond6: "+dir.getPond6());

        TextView trasiegoplano = (TextView) v.findViewById(R.id.trasiegoplano);
        trasiegoplano.setText("Trasiego Plano: "+dir.getTrasiegoplano());

        TextView pond7 = (TextView) v.findViewById(R.id.pond7);
        pond7.setText("Pond7: "+dir.getPond7());

        TextView tiposuelo = (TextView) v.findViewById(R.id.tipodesuelo);
        tiposuelo.setText("Tipo Suelo: "+dir.getTiposuelo());

        TextView pond8 = (TextView) v.findViewById(R.id.pond8);
        pond8.setText("Pond8: "+dir.getPond8());

        TextView piedraslote = (TextView) v.findViewById(R.id.piedraslote);
        piedraslote.setText("#Piedras/Lote"+dir.getPiedraslote());

        TextView pond9 = (TextView) v.findViewById(R.id.pond9);
        pond9.setText("Pond8: "+dir.getPond9());

        TextView desnivelsurco = (TextView) v.findViewById(R.id.desnivelsurco);
        desnivelsurco.setText("Desnivel Surco/Ronda"+dir.getDesnivelsurco());

        TextView pond10 = (TextView) v.findViewById(R.id.pond10);
        pond10.setText("Pond10: "+dir.getPond10());

        TextView total1 = (TextView) v.findViewById(R.id.total1);
        total1.setText("Total1: "+dir.getTotal1());

        TextView classugerida = (TextView) v.findViewById(R.id.classugerida);
        classugerida.setText("Clas. Sugerida: "+dir.getClassugerida());

        TextView nombresupervisor = (TextView) v.findViewById(R.id.nombresupervisor);
        nombresupervisor.setText("Nombre Supervisor: "+dir.getNombresupervisor());

        TextView mecanizable = (TextView) v.findViewById(R.id.mecanizable);
        mecanizable.setText("%Mecanizable"+dir.getMecanizable());

        TextView ha = (TextView) v.findViewById(R.id.ha);
        ha.setText("Ha.: "+dir.getHa());

        TextView total2 = (TextView) v.findViewById(R.id.total2);
        total2.setText("Total2: "+dir.getTotal2());
        return v;
    }
}

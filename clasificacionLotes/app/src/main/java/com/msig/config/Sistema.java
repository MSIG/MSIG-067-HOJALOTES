package com.msig.config;

import android.content.Context;
import android.content.SharedPreferences;

public class Sistema {
	private final String SHARED_PREFERENCES_FILE = "MSIG_LABORES_SYSTEM";
	private final String SISTEMA = "NAME_SYSTEM";
	private Context sistema;
	
	public Sistema(Context sistema) {		
		this.sistema = sistema;
	}
	
	private SharedPreferences getSettings(){
		return sistema.getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
	}
	
	public String getSistem(){
		return getSettings().getString(SISTEMA, null);
	}
	
	public void setSistem(String s){
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putString(SISTEMA, s);
		editor.commit();

	}
	
}

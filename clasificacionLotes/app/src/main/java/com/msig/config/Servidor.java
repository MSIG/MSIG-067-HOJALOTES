package com.msig.config;

import android.content.Context;
import android.content.SharedPreferences;

public class Servidor {
	private final String SHARED_PREFERENCES_FILE = "MSIG_LABORES_SERVER";
	private final String SERVER = "LINK_SERVER";
	private Context server;
	
	public Servidor(Context server) {		
		this.server = server;
	}

	private SharedPreferences getSettings(){
		return server.getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
	}
	
	public String getServer(){
		return getSettings().getString(SERVER, null);
	}
	
	public void setServer(String s){
		SharedPreferences.Editor editor = getSettings().edit();
		editor.putString(SERVER, s);
		editor.commit();
	}
	
}

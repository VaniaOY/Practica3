package com.reserva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Actividad2 extends Activity {

	String nombre = "", fecha = "", hora = "";
	int personas = 0;
	TextView muestraDatos;

	EditText Telefono, Edad, Email, Disfrutar,Servicio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);

		muestraDatos = (TextView) findViewById(R.id.muestraDatos);
		Telefono = (EditText) findViewById(R.id.Telefono);
		Edad = (EditText) findViewById(R.id.Edad);
		Email = (EditText) findViewById(R.id.Email);
		Disfrutar = (EditText) findViewById(R.id.Disfrutar);
		Servicio = (EditText) findViewById(R.id.Servicio);

		Bundle recibe = new Bundle();
		recibe = this.getIntent().getExtras();

		nombre = recibe.getString("nombre");
		personas = recibe.getInt("personas");
		fecha = recibe.getString("fecha");
		hora = recibe.getString("hora");

		muestraDatos.setText("Reservacion a nombre de:\n" + nombre + "\n" + personas
				+ " personas\nFecha: " + fecha + "\nHora: " + hora + "\n");

	}
	public void reserva(View v) {
		Intent manda = new Intent(this, MainActivity.class);
		Bundle datos = new Bundle();
		datos.putString("Telefono", Telefono.getText().toString().trim());
		datos.putString("Edad", Edad.getText().toString().trim());
		datos.putString("Email", Email.getText().toString().trim());
		datos.putString("Disfrutar", Disfrutar.getText().toString().trim());
		datos.putString("Servicio", Servicio.getText().toString().trim());

		manda.putExtras(datos);
		finish();
		startActivity(manda);
	}

    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
        finish();
        startActivity(envia);
    }

}

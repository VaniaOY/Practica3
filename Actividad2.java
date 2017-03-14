package com.reserva;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Actividad2 extends Activity implements SeekBar.OnSeekBarChangeListener,
		View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	String nombre = "", fecha = "", hora = "";
	int personas = 0;
	TextView muestraDatos;


	EditText nombren;
	TextView cuantasPersonas;
	Button fechan, horan;
	SeekBar barraPersonas;
	String cuantasPersonasFormat = "";
	SimpleDateFormat horaFormato, fechaFormato;
	String fechaSel = "", horaSel = "";
	Date fechaConv;
	String numPersonas = "";
	Calendar calendario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad2);

		muestraDatos = (TextView) findViewById(R.id.muestraDatos);

		Bundle recibe = new Bundle();
		recibe = this.getIntent().getExtras();

		nombre = recibe.getString("nombre");
		personas = recibe.getInt("personas");
		fecha = recibe.getString("fecha");
		hora = recibe.getString("hora");

		muestraDatos.setText("Reservacion a nombre de:\n" + nombre + "\n" + personas
				+ " personas\nFecha: " + fecha + "\nHora: " + hora + "\n");

//Datos nuevos
		cuantasPersonas = (TextView) findViewById(R.id.cuantasPersonas);
		barraPersonas = (SeekBar) findViewById(R.id.personasm);

		fechan = (Button) findViewById(R.id.fechan);
		horan = (Button) findViewById(R.id.horan);

		barraPersonas.setOnSeekBarChangeListener(this);

		nombren = (EditText) findViewById(R.id.nombren);

		cuantasPersonasFormat = cuantasPersonas.getText().toString();
		// cuantasPersonasFormat = "personas: %d";
		cuantasPersonas.setText("Personas: 1"); // condicion inicial

		// Para seleccionar la fecha y la hora
		Calendar fechaSeleccionada = Calendar.getInstance();
		fechaSeleccionada.set(Calendar.HOUR_OF_DAY, 12); // hora inicial
		fechaSeleccionada.clear(Calendar.MINUTE); // 0
		fechaSeleccionada.clear(Calendar.SECOND); // 0

		// formatos de la fecha y hora
		fechaFormato = new SimpleDateFormat(fechan.getText().toString());
		horaFormato = new SimpleDateFormat("HH:mm");
		// horaFormato = new SimpleDateFormat(hora.getText().toString());

		// La primera vez mostramos la fecha actual
		Date fechaReservacion = fechaSeleccionada.getTime();
		fechaSel = fechaFormato.format(fechaReservacion);
		fechan.setText(fechaSel); // fecha en el

		horaSel = horaFormato.format(fechaReservacion);
		// boton
		horan.setText(horaSel); // hora en el boton

		// Otra forma de ocupar los botones
		fechan.setOnClickListener(this);
		horan.setOnClickListener(this);


	}
	public void onProgressChanged(SeekBar barra, int progreso,
								  boolean delUsuario) {

		numPersonas = String.format(cuantasPersonasFormat,
				barraPersonas.getProgress() + 1);
		personas = barraPersonas.getProgress() + 1; // este es el valor que se
		// guardara en la BD
		// Si no se mueve la barra, enviamos el valor personas = 1
		cuantasPersonas.setText(numPersonas);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
	}

	@Override
	public void onClick(View v) {
		if (v == fechan) {
			Calendar calendario = parseCalendar(fechan.getText(), fechaFormato);
			new DatePickerDialog(this, this, calendario.get(Calendar.YEAR),
					calendario.get(Calendar.MONTH),
					calendario.get(Calendar.DAY_OF_MONTH)).show();
		} else if (v == horan) {
			Calendar calendario = parseCalendar(horan.getText(), horaFormato);
			new TimePickerDialog(this, this,
					calendario.get(Calendar.HOUR_OF_DAY),
					calendario.get(Calendar.MINUTE), false) // /true = 24 horas
					.show();
		}
	}

	private Calendar parseCalendar(CharSequence text,
								   SimpleDateFormat fechaFormat2) {
		try {
			fechaConv = fechaFormat2.parse(text.toString());
		} catch (ParseException e) { // import java.text.ParsedExc
			throw new RuntimeException(e);
		}
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaConv);
		return calendario;
	}

	@Override
	public void onDateSet(DatePicker picker, int anio, int mes, int dia) {
		calendario = Calendar.getInstance();
		calendario.set(Calendar.YEAR, anio);
		calendario.set(Calendar.MONTH, mes);
		calendario.set(Calendar.DAY_OF_MONTH, dia);

		fechaSel = fechaFormato.format(calendario.getTime());
		fechan.setText(fechaSel);

	}

	public void onTimeSet(TimePicker picker, int horas, int minutos) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, horas);
		calendar.set(Calendar.MINUTE, minutos);

		horaSel = horaFormato.format(calendar.getTime());
		horan.setText(horaSel);
	}

    public void hacerOtraReserva(View v) {
        Intent envia = new Intent(this, MainActivity.class);
		Bundle datos = new Bundle();
		datos.putString("nombre", nombren.getText().toString().trim());
		datos.putInt("personas", personas);
		datos.putString("fecha", fechaSel);
		datos.putString("hora", horaSel);
		datos.putBoolean("Otro", true);
		envia.putExtras(datos);
		finish();
        startActivity(envia);
    }

}

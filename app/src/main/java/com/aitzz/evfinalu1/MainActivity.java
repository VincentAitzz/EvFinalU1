package com.aitzz.evfinalu1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotaAdapter notaAdapter;
    private ArrayList<NotaModel> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        listaNotas = new ArrayList<>();
        notaAdapter = new NotaAdapter(listaNotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notaAdapter);

        // Configurar el botón para agregar notas
        findViewById(R.id.buttonAgregarNota).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoRegistrarNota();
            }
        });
    }

    private void mostrarDialogoRegistrarNota() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_registrar_nota, null);
        builder.setView(dialogView);

        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        dialogView.startAnimation(scaleAnimation);

        EditText editTextTitulo = dialogView.findViewById(R.id.editTextTitulo);
        EditText editTextDescripcion = dialogView.findViewById(R.id.editTextDescripcion);
        Button buttonSeleccionarFecha = dialogView.findViewById(R.id.buttonSeleccionarFecha);
        TextView textViewFechaSeleccionada = dialogView.findViewById(R.id.textViewFechaSeleccionada);
        Button buttonSeleccionarHora = dialogView.findViewById(R.id.buttonSeleccionarHora);
        TextView textViewHoraSeleccionada = dialogView.findViewById(R.id.textViewHoraSeleccionada);
        Button buttonGuardar = dialogView.findViewById(R.id.buttonGuardar);

        // Configurar el Spinner para los colores
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colores_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        AlertDialog dialog = builder.create();

        // Manejar la selección de fecha
        buttonSeleccionarFecha.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                String fechaSeleccionada = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                textViewFechaSeleccionada.setText(fechaSeleccionada);
            }, year, month, day);
            datePickerDialog.show();
        });

        // Manejar la selección de hora
        buttonSeleccionarHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
                String horaSeleccionada = String.format("%02d:%02d", selectedHour, selectedMinute);
                textViewHoraSeleccionada.setText(horaSeleccionada);
            }, hour, minute, true);
            timePickerDialog.show();
        });

        buttonGuardar.setOnClickListener(v -> {
            String titulo = editTextTitulo.getText().toString();
            String descripcion = editTextDescripcion.getText().toString();
            String fecha = textViewFechaSeleccionada.getText().toString();
            String hora = textViewHoraSeleccionada.getText().toString();

            NotaModel nuevaNota = new NotaModel(titulo, descripcion, fecha, hora);
            listaNotas.add(nuevaNota);
            notaAdapter.notifyDataSetChanged();
            dialog.dismiss();
            dialogView.startAnimation(fadeAnimation);
        });

        dialog.show();
    }
}
package com.unir.pertomap;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        List<Categoria> categorias = new ArrayList<>();

        categorias.add(new Categoria("Comércio", "commercial", R.drawable.ic_commerce));
        categorias.add(new Categoria("Alimentação", "catering", R.drawable.ic_food));
        categorias.add(new Categoria("Educação", "education", R.drawable.ic_education));
        categorias.add(new Categoria("Entretenimento", "entertainment", R.drawable.ic_entertainment));
        categorias.add(new Categoria("Saúde", "healthcare", R.drawable.ic_health));
        categorias.add(new Categoria("Lazer", "leisure", R.drawable.ic_leisure));
        categorias.add(new Categoria("Natureza", "natural", R.drawable.ic_nature));
        categorias.add(new Categoria("Religião", "religion", R.drawable.ic_religion));
        categorias.add(new Categoria("Serviços", "service", R.drawable.ic_service));
        categorias.add(new Categoria("Esporte", "sport", R.drawable.ic_sport));
        categorias.add(new Categoria("Turismo", "tourism", R.drawable.ic_tourism));
        categorias.add(new Categoria("Transporte", "transport", R.drawable.ic_transport));

        rv.setAdapter(new CategoriaAdapter(categorias, categoria -> {
            Intent i = new Intent(this, MapaActivity.class);
            i.putExtra("categoria", categoria.apiCategoria);
            startActivity(i);
        }));
    }

}
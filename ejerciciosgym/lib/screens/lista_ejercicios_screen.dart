import 'package:ejerciciosgym/core/widgets/elemento_ejercicio_widget.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ListaEjerciciosScreen extends StatelessWidget {
  const ListaEjerciciosScreen({super.key});

  @override
  Widget build(BuildContext context) {    
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return Scaffold(
      appBar: AppBar(title: const Text('Lista de ejercicios')),
      body: FutureBuilder(
        future: provider.cargarEjercicios(context),
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            return Center(
              child: Text(' Error al cargar ejercicios: ${snapshot.error}'),
            );
          }

          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }

          final ejercicios = snapshot.data!;
          return ListView.separated(
            itemCount: ejercicios.length,
            itemBuilder: (context, index) =>
                ElementoEjercicioWidget(ejercicio: ejercicios[index]),
            separatorBuilder: (BuildContext context, int index) =>
                Divider(height: 20, color: Colors.blue),
          );
        },
      ),
    );
  }
}

import 'package:ejerciciosgym/models/ejercicio_model.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ListaEjerciciosScreen extends StatelessWidget {
  const ListaEjerciciosScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Lista de ejercicios')),
      body: Consumer<EjerciciosProvider>(
        builder: (context, provider, child) {
          if (provider.ejercicios.isEmpty) provider.cargar();

          final ejercicios = provider.ejercicios;
          return ListView.builder(
            itemCount: ejercicios.length,
            itemBuilder: (context, index) =>
                _Ejercicio(ejercicio: ejercicios[index]),
          );
        },
      ),
    );
  }
}

class _Ejercicio extends StatelessWidget {
  final EjercicioModel ejercicio;

  const _Ejercicio({required this.ejercicio});

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return FutureBuilder(
      future: provider.getImagen(ejercicio.id), 
      builder: (context, snapshot) {
        // rellenar con fade image
      },
    );
  }
}

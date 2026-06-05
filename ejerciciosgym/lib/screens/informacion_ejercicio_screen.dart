import 'package:ejerciciosgym/core/widgets/elemento_musculo_widget.dart';
import 'package:ejerciciosgym/models/ejercicio_imagen_model.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class InformacionEjercicioScreen extends StatelessWidget {
  const InformacionEjercicioScreen({super.key});

  @override
  Widget build(BuildContext context) {
    EjercicioImagenModel ejercicioImagen =
        ModalRoute.of(context)!.settings.arguments as EjercicioImagenModel;

    final size = MediaQuery.of(context).size;

    return Scaffold(
      body: CustomScrollView(
        slivers: [
          _cabecera(size, ejercicioImagen),
          SliverToBoxAdapter(child: _informacionEjercicio(ejercicioImagen)),
          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 15),
              child: Divider(thickness: 1),
            ),
          ),
          SliverToBoxAdapter(
            child: Padding(
              padding: const EdgeInsets.all(15),
              child: Text(
                'Músculos involucrados',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                textAlign: TextAlign.center,
              ),
            ),
          ),
          SliverToBoxAdapter(
            child: _listaMusculos(context, ejercicioImagen.ejercicio.id),
          ),
        ],
      ),
    );
  }

  Widget _cabecera(Size size, EjercicioImagenModel ejercicioImagen) {
    return SliverAppBar(
      expandedHeight: size.height * 0.25,
      flexibleSpace: FlexibleSpaceBar(
        titlePadding: EdgeInsets.zero,
        title: Align(
          alignment: Alignment
              .bottomCenter, // establece el fondo adecuado con el titulo
          child: Container(
            width: double.infinity,
            color: Colors.black54,
            child: Text(
              ejercicioImagen.ejercicio.nombre,
              style: TextStyle(color: Colors.white, fontSize: 18),
              textAlign: TextAlign.center,
            ),
          ),
        ),
        background: Image.memory(ejercicioImagen.imagen, fit: BoxFit.cover),
      ),
    );
  }

  Widget _informacionEjercicio(EjercicioImagenModel ejercicioImagen) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 12),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(height: 20),
              Align(
                alignment: AlignmentGeometry.centerStart,
                child: Text(
                  ejercicioImagen.ejercicio.pesoMinimo != null &&
                          ejercicioImagen.ejercicio.pesoMaximo != null
                      ? '${ejercicioImagen.ejercicio.descripcion}\nPeso mínimo: ${ejercicioImagen.ejercicio.pesoMinimo} kg\nPeso máximo: ${ejercicioImagen.ejercicio.pesoMaximo} kg'
                      : ejercicioImagen.ejercicio.descripcion,
                  style: TextStyle(fontSize: 15),
                ),
              ),
              SizedBox(height: 10),
            ],
          ),
        ),
      ],
    );
  }

  Widget _listaMusculos(BuildContext context, int idEjercicio) {
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return FutureBuilder(
      future: provider.cargarMusculosInvolucrados(context, idEjercicio),
      builder: (context, snapshot) {
        if (snapshot.hasError) {
          return Center(
            child: Text(' Error al cargar ejercicios: ${snapshot.error}'),
          );
        }

        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        }

        final musculosInvolucrados = snapshot.data!;
        return Column(
          children: musculosInvolucrados
              .map((m) => ElementoMusculoWidget(musculoInvolucrado: m))
              .toList(),
        );
      },
    );
  }
}

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

    return Scaffold(
      appBar: AppBar(title: Text(ejercicioImagen.ejercicio.nombre)),
      body: Column(
        children: [
          Expanded(
            flex: 2,
            child: _imagenEjercicioNombreDescripcion(ejercicioImagen),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 15),
            child: Divider(thickness: 1),
          ),
          Text(
            'Músculos involucrados',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 10),
          Expanded(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10),
              child: _listaMusculos(context, ejercicioImagen.ejercicio.id),
            ),
          ),
        ],
      ),
    );
  }

  Widget _imagenEjercicioNombreDescripcion(
    EjercicioImagenModel ejercicioImagen,
  ) {
    return Column(
      children: [
        Expanded(
          flex: 3,
          child: Card(
            elevation: 8,
            shadowColor: Colors.black38,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(15),
            ),
            clipBehavior: Clip.hardEdge,
            margin: const EdgeInsets.fromLTRB(10, 10, 10, 5),
            child: Image.memory(ejercicioImagen.imagen, fit: BoxFit.cover),
          ),
        ),
        Expanded(
          flex: 2,
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(height: 10),
                Text(
                  ejercicioImagen.ejercicio.nombre,
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.blue.shade700,
                  ),
                ),
                SizedBox(height: 6),
                Divider(height: 1),
                SizedBox(height: 8),
                Expanded(
                  child: SingleChildScrollView(
                    child: Text(
                      ejercicioImagen.ejercicio.descripcion,
                      style: TextStyle(
                        fontSize: 15,
                        color: Colors.grey.shade700,
                        height: 1.4,
                      ),
                    ),
                  ),
                ),
              ],
            ),
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
        return ListView.builder(
          itemCount: musculosInvolucrados.length,
          itemBuilder: (context, index) => ElementoMusculoWidget(
            musculoInvolucrado: musculosInvolucrados[index],
          ),
        );
      },
    );
  }
}

import 'package:ejerciciosgym/core/widgets/elemento_ejercicio_widget.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class EjerciciosSearchDelegate extends SearchDelegate {
  @override
  String? get searchFieldLabel => 'Buscar ejercicios';

  @override
  List<Widget>? buildActions(BuildContext context) {
    return [
      IconButton(
        onPressed: () {
          query = '';
          showSuggestions(context);
        },
        icon: Icon(Icons.clear),
      ),
    ];
  }

  @override
  Widget? buildLeading(BuildContext context) {
    return IconButton(
      onPressed: () => close(context, null),
      icon: Icon(Icons.arrow_back),
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return _cargarEjercicios(context);
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return _cargarEjercicios(context);
  }

  FutureBuilder _cargarEjercicios(BuildContext context) {
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return FutureBuilder(
      future: provider.cargarEjerciciosPorNombre(context, query),
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
    );
  }
}

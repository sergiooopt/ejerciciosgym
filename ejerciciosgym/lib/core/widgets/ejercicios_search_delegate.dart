import 'package:ejerciciosgym/core/widgets/elemento_ejercicio_widget.dart';
import 'package:ejerciciosgym/core/widgets/error_ejercicios_widget.dart';
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
    final provider = Provider.of<EjerciciosProvider>(context, listen: false);

    return FutureBuilder(
      future: provider.cargarEjerciciosPorNombre(context, query),
      builder: (context, snapshot) {
        if (snapshot.hasError) return ErrorEjerciciosWidget(error: snapshot.error);

        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        }
        final ejercicios = snapshot.data!;
        return ListView.builder(
          itemCount: ejercicios.length,
          itemBuilder: (context, index) =>
              ElementoEjercicioWidget(ejercicioImagen: ejercicios[index]),
        );
      },
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return buildResults(context);
  }
}

import 'package:flutter/material.dart';

class ErrorEjerciciosWidget extends StatelessWidget {
  const ErrorEjerciciosWidget({super.key, required this.error});

  final Object? error;

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.cloud_sync_outlined, color: Colors.red, size: 80),
          SizedBox(height: 20),
          Text(
            'Error al listar los ejercicios',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 10),
          error != null
              ? Text(error.toString(), style: TextStyle(fontSize: 12))
              : Text(
                  'Código de error desconocido',
                  style: TextStyle(fontSize: 12),
                ),
        ],
      ),
    );
  }
}

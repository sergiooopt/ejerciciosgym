import 'package:flutter/material.dart';

class ErrorEjerciciosWidget extends StatelessWidget {
  const ErrorEjerciciosWidget({super.key, required this.error});

  final Object? error;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 10, right: 10),
      child: Center(
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
                ? Text(
                    error.toString(),
                    style: TextStyle(fontSize: 12),
                    textAlign: TextAlign.center,
                  )
                : Text(
                    'Error desconocido',
                    style: TextStyle(fontSize: 12),
                    textAlign: TextAlign.center,
                  ),
          ],
        ),
      ),
    );
  }
}

import 'package:flutter/material.dart';

class InformacionEjercicioScreen extends StatelessWidget {
  const InformacionEjercicioScreen({super.key});

  @override
  Widget build(BuildContext context) {
    String nombreEjercicio =
        ModalRoute.of(context)!.settings.arguments as String;

    return Scaffold(
      appBar: AppBar(title: Text(nombreEjercicio)),
      body: Container(),
    );
  }
}

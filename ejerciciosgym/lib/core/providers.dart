import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/providers/configuracion_provider.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Providers extends StatelessWidget {
  final String? ipServidor;

  const Providers({super.key, this.ipServidor});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<EjerciciosProvider>(
          create: (context) => EjerciciosProvider(),
        ),
        ChangeNotifierProvider<ConfiguracionProvider>(
          create: (context) => ConfiguracionProvider(ipServidor: ipServidor),
        ),
      ],
      child: App(),
    );
  }
}

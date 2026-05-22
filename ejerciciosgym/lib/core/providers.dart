import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/providers/configuracion_provider.dart';
import 'package:ejerciciosgym/providers/ejercicios_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Providers extends StatelessWidget {
  const Providers({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<EjerciciosProvider>(
          create: (context) => EjerciciosProvider(),
        ),
        ChangeNotifierProvider<ConfiguracionProvider>(
          create: (context) => ConfiguracionProvider(),
        ),
      ],
      child: App(),
    );
  }
}

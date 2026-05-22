import 'package:ejerciciosgym/screens/configuracion_screen.dart';
import 'package:ejerciciosgym/screens/home_screen.dart';
import 'package:ejerciciosgym/screens/informacion_ejercicio_screen.dart';
import 'package:ejerciciosgym/screens/lista_ejercicios_screen.dart';
import 'package:flutter/material.dart';

class App extends StatelessWidget {
  static const String home = "HomeScreen";
  static const String listaEjercicios = "ListaEjerciciosScreen";
  static const String configuracion = "ConfiguracionScreen";
  static const String informacionEjercicio = "InformacionEjercicioScreen";

  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomeScreen(),
      routes: _getRutas(),
      theme: ThemeData.light().copyWith(appBarTheme: _getAppBarTheme()),
      darkTheme: ThemeData.dark().copyWith(appBarTheme: _getAppBarTheme()),
      themeMode: ThemeMode.system,
      debugShowCheckedModeBanner: false,
    );
  }

  Map<String, WidgetBuilder> _getRutas() => {
    home: (context) => HomeScreen(),
    listaEjercicios: (context) => ListaEjerciciosScreen(),
    configuracion: (context) => ConfiguracionScreen(),
    informacionEjercicio: (context) => InformacionEjercicioScreen(),
  };

  AppBarTheme _getAppBarTheme() => AppBarTheme(
    backgroundColor: Colors.blue,
    foregroundColor: Colors.white,
    centerTitle: true,
  );
}

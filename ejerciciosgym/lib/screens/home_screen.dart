import 'dart:io';

import 'package:ejerciciosgym/core/app.dart';
import 'package:ejerciciosgym/core/widgets/ejercicios_search_delegate.dart';
import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Ejercicios Gym')),
      body: Center(child: Image.asset('assets/logo.png')),
      drawer: Drawer(
        child: ListView(
          children: [            
            ListTile(
              leading: Icon(Icons.format_list_bulleted),
              title: Text('Ejercicios'),
              onTap: () => Navigator.of(context).pushNamed(App.listaEjercicios),
            ),
            ListTile(
              leading: Icon(Icons.search),
              title: Text('Buscar ejercicios'),
              onTap: () => showSearch(
                context: context,
                delegate: EjerciciosSearchDelegate(),
              ),
            ),
            ListTile(
              leading: Icon(Icons.construction),
              title: Text('Configuración'),
              onTap: () => Navigator.of(context).pushNamed(App.configuracion),
            ),
            ListTile(
              leading: Icon(Icons.exit_to_app),
              title: Text('Salir de la aplicación'),
              onTap: () => exit(0),
            ),
          ],
        ),
      ),
    );
  }
}

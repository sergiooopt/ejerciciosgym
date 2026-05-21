import 'dart:io';

import 'package:ejerciciosgym/core/app.dart';
import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Ejercicios Gym')),
      body: Center(child: Image.asset('assets/imagenes/logo.png')),
      drawer: Drawer(
        child: ListView(
          padding: const EdgeInsets.all(0),
          children: [
            SizedBox(height: 20),
            ListTile(
              leading: Icon(Icons.format_list_bulleted),
              title: Text('Lista de ejercicios'),
              onTap: () => Navigator.of(context).pushNamed(App.listaEjercicios),
            ),
            ListTile(
              leading: Icon(Icons.manage_search),
              title: Text('Filtrar ejercicios'),
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

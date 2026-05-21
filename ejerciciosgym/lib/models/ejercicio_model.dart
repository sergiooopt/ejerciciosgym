class EjercicioModel {
  final int id;
  final String nombre;
  final String descripcion;
  final String rutaImagen;
  final int? pesoMinimo;
  final int? pesoMaximo;

  EjercicioModel({
    required this.id,
    required this.nombre,
    required this.descripcion,
    required this.rutaImagen,
    this.pesoMinimo,
    this.pesoMaximo,
  });

  factory EjercicioModel.fromMap({required Map<String, dynamic> map}) =>
      EjercicioModel(
        id: map['id'],
        nombre: map['nombre'],
        descripcion: map['descripcion'],
        rutaImagen: map['rutaImagen'],
        pesoMinimo: map['pesoMinimo'],
        pesoMaximo: map['pesoMaximo'],
      );

  Map<String, dynamic> toMap() => {
    'id': id,
    'nombre': nombre,
    'descripcion': descripcion,
    'rutaImagen': rutaImagen,
    'pesoMinimo': pesoMinimo,
    'pesoMaximo': pesoMaximo,
  };
}

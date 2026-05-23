class EjercicioMusculosModel {
  int idEjercicio;
  int idMusculo;
  String descripcion;
  bool esDirecto;
  int porcentajeActivacion;

  EjercicioMusculosModel({
    required this.idEjercicio,
    required this.idMusculo,
    required this.descripcion,
    required this.esDirecto,
    required this.porcentajeActivacion,
  });

  factory EjercicioMusculosModel.fromMap({required Map<String, dynamic> map}) =>
      EjercicioMusculosModel(
        idEjercicio: map['idEjercicio'],
        idMusculo: map['idMusculo'],
        descripcion: map['descripcion'],
        esDirecto: map['esDirecto'],
        porcentajeActivacion: map['porcentajeActivacion'],
      );

  Map<String, dynamic> toMap() => {
    "idEjercicio": idEjercicio,
    "idMusculo": idMusculo,
    "descripcion": descripcion,
    "esDirecto": esDirecto,
    "porcentajeActivacion": porcentajeActivacion,
  };
}

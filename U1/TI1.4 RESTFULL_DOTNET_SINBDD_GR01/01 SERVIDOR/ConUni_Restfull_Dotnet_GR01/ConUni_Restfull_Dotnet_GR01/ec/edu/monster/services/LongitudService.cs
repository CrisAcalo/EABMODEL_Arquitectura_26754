using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.validators;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services
{
    /// <summary>
    /// Servicio para realizar conversiones de unidades de longitud
    /// Soporta: Milla, Metro, Pulgada
    /// </summary>
    public class LongitudService
    {
  #region Métodos con validación de string

        /// <summary>
        /// Convierte de Milla a Metro (con validación de string)
        /// </summary>
   public ConversionResultModel ConvertirMillaAMetro(string millasString)
        {
            var error = BaseValidator.ValidarStringPositivo(millasString, LongitudConstants.MILLA, out double millas);
            if (error != null)
           return ConversionResultModel.Fallo(error);

return ConvertirMillaAMetro(millas);
        }

        /// <summary>
     /// Convierte de Metro a Milla (con validación de string)
/// </summary>
        public ConversionResultModel ConvertirMetroAMilla(string metrosString)
        {
            var error = BaseValidator.ValidarStringPositivo(metrosString, LongitudConstants.METRO, out double metros);
if (error != null)
              return ConversionResultModel.Fallo(error);

       return ConvertirMetroAMilla(metros);
    }

        /// <summary>
        /// Convierte de Milla a Pulgada (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirMillaAPulgada(string millasString)
        {
     var error = BaseValidator.ValidarStringPositivo(millasString, LongitudConstants.MILLA, out double millas);
     if (error != null)
              return ConversionResultModel.Fallo(error);

            return ConvertirMillaAPulgada(millas);
        }

        /// <summary>
     /// Convierte de Pulgada a Milla (con validación de string)
        /// </summary>
      public ConversionResultModel ConvertirPulgadaAMilla(string pulgadasString)
        {
   var error = BaseValidator.ValidarStringPositivo(pulgadasString, LongitudConstants.PULGADA, out double pulgadas);
      if (error != null)
    return ConversionResultModel.Fallo(error);

    return ConvertirPulgadaAMilla(pulgadas);
        }

        /// <summary>
        /// Convierte de Metro a Pulgada (con validación de string)
        /// </summary>
public ConversionResultModel ConvertirMetroAPulgada(string metrosString)
        {
            var error = BaseValidator.ValidarStringPositivo(metrosString, LongitudConstants.METRO, out double metros);
            if (error != null)
         return ConversionResultModel.Fallo(error);

    return ConvertirMetroAPulgada(metros);
 }

        /// <summary>
        /// Convierte de Pulgada a Metro (con validación de string)
        /// </summary>
  public ConversionResultModel ConvertirPulgadaAMetro(string pulgadasString)
        {
   var error = BaseValidator.ValidarStringPositivo(pulgadasString, LongitudConstants.PULGADA, out double pulgadas);
 if (error != null)
       return ConversionResultModel.Fallo(error);

    return ConvertirPulgadaAMetro(pulgadas);
        }

 #endregion

        #region Métodos internos con double (para reutilización)

      /// <summary>
      /// Convierte de Milla a Metro
 /// </summary>
        private ConversionResultModel ConvertirMillaAMetro(double millas)
    {
            // Validar entrada
     var error = BaseValidator.ValidarValorPositivo(millas, LongitudConstants.MILLA);
            if (error != null)
          return ConversionResultModel.Fallo(error);

     // Realizar conversión
            double metros = millas * LongitudConstants.MILLA_A_METRO;

            var resultado = new UnidadConversionModel(
   millas,
     metros,
                LongitudConstants.MILLA,
          LongitudConstants.METRO,
         "Longitud",
  LongitudConstants.MILLA_A_METRO
 );

   return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
/// Convierte de Metro a Milla
        /// </summary>
        private ConversionResultModel ConvertirMetroAMilla(double metros)
        {
         var error = BaseValidator.ValidarValorPositivo(metros, LongitudConstants.METRO);
      if (error != null)
      return ConversionResultModel.Fallo(error);

            double millas = metros * LongitudConstants.METRO_A_MILLA;

   var resultado = new UnidadConversionModel(
       metros,
             millas,
    LongitudConstants.METRO,
        LongitudConstants.MILLA,
    "Longitud",
       LongitudConstants.METRO_A_MILLA
  );

    return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Milla a Pulgada
        /// </summary>
        private ConversionResultModel ConvertirMillaAPulgada(double millas)
      {
    var error = BaseValidator.ValidarValorPositivo(millas, LongitudConstants.MILLA);
       if (error != null)
        return ConversionResultModel.Fallo(error);

            double pulgadas = millas * LongitudConstants.MILLA_A_PULGADA;

     var resultado = new UnidadConversionModel(
    millas,
                pulgadas,
  LongitudConstants.MILLA,
          LongitudConstants.PULGADA,
          "Longitud",
       LongitudConstants.MILLA_A_PULGADA
            );

            return ConversionResultModel.Exito(resultado);
        }

 /// <summary>
        /// Convierte de Pulgada a Milla
  /// </summary>
      private ConversionResultModel ConvertirPulgadaAMilla(double pulgadas)
        {
            var error = BaseValidator.ValidarValorPositivo(pulgadas, LongitudConstants.PULGADA);
if (error != null)
 return ConversionResultModel.Fallo(error);

            double millas = pulgadas * LongitudConstants.PULGADA_A_MILLA;

            var resultado = new UnidadConversionModel(
       pulgadas,
   millas,
       LongitudConstants.PULGADA,
         LongitudConstants.MILLA,
      "Longitud",
LongitudConstants.PULGADA_A_MILLA
   );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Metro a Pulgada
        /// </summary>
   private ConversionResultModel ConvertirMetroAPulgada(double metros)
        {
   var error = BaseValidator.ValidarValorPositivo(metros, LongitudConstants.METRO);
            if (error != null)
       return ConversionResultModel.Fallo(error);

 double pulgadas = metros * LongitudConstants.METRO_A_PULGADA;

    var resultado = new UnidadConversionModel(
       metros,
        pulgadas,
       LongitudConstants.METRO,
      LongitudConstants.PULGADA,
         "Longitud",
           LongitudConstants.METRO_A_PULGADA
  );

   return ConversionResultModel.Exito(resultado);
        }

   /// <summary>
        /// Convierte de Pulgada a Metro
        /// </summary>
  private ConversionResultModel ConvertirPulgadaAMetro(double pulgadas)
        {
            var error = BaseValidator.ValidarValorPositivo(pulgadas, LongitudConstants.PULGADA);
     if (error != null)
         return ConversionResultModel.Fallo(error);

       double metros = pulgadas * LongitudConstants.PULGADA_A_METRO;

            var resultado = new UnidadConversionModel(
        pulgadas,
          metros,
              LongitudConstants.PULGADA,
                LongitudConstants.METRO,
       "Longitud",
     LongitudConstants.PULGADA_A_METRO
            );

      return ConversionResultModel.Exito(resultado);
     }

        #endregion
    }
}

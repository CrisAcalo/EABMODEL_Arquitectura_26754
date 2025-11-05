using System;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.services;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.constants;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    /// <summary>
    /// Servicio WCF para conversiones de longitud
    /// Actúa como capa de presentación delegando la lógica de negocio
    /// </summary>
    public class LongitudService : ILongitudService
    {
        private readonly LongitudBusinessService _longitudBusinessService;

        /// <summary>
        /// Constructor que inicializa el servicio de negocio
        /// </summary>
        public LongitudService()
        {
            _longitudBusinessService = new LongitudBusinessService();
        }

        #region Conversiones Milla <-> Metro

        /// <summary>
        /// Convierte millas a metros
        /// </summary>
        /// <param name="millas">Valor en millas como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult MillaAMetro(string millas)
        {
            try
            {
                return _longitudBusinessService.ConvertirMillaAMetro(millas);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.MILLA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte metros a millas
        /// </summary>
        /// <param name="metros">Valor en metros como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult MetroAMilla(string metros)
        {
            try
            {
                return _longitudBusinessService.ConvertirMetroAMilla(metros);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.METRO,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Milla <-> Pulgada

        /// <summary>
        /// Convierte millas a pulgadas
        /// </summary>
        /// <param name="millas">Valor en millas como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult MillaAPulgada(string millas)
        {
            try
            {
                return _longitudBusinessService.ConvertirMillaAPulgada(millas);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.MILLA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte pulgadas a millas
        /// </summary>
        /// <param name="pulgadas">Valor en pulgadas como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult PulgadaAMilla(string pulgadas)
        {
            try
            {
                return _longitudBusinessService.ConvertirPulgadaAMilla(pulgadas);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.PULGADA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Metro <-> Pulgada

        /// <summary>
        /// Convierte metros a pulgadas
        /// </summary>
        /// <param name="metros">Valor en metros como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult MetroAPulgada(string metros)
        {
            try
            {
                return _longitudBusinessService.ConvertirMetroAPulgada(metros);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.METRO,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte pulgadas a metros
        /// </summary>
        /// <param name="pulgadas">Valor en pulgadas como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult PulgadaAMetro(string pulgadas)
        {
            try
            {
                return _longitudBusinessService.ConvertirPulgadaAMetro(pulgadas);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    LongitudConstants.PULGADA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion
    }
}

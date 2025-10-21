using System;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.models;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.services;
using ConUni_Soap_DotNet_GR01.ec.edu.monster.constants;

namespace ConUni_Soap_DotNet_GR01.ec.edu.monster.ws
{
    /// <summary>
    /// Servicio WCF para conversiones de masa
    /// Actúa como capa de presentación delegando la lógica de negocio
    /// </summary>
    public class MasaService : IMasaService
    {
        private readonly MasaBusinessService _masaBusinessService;

        /// <summary>
        /// Constructor que inicializa el servicio de negocio
        /// </summary>
        public MasaService()
        {
            _masaBusinessService = new MasaBusinessService();
        }

        #region Conversiones Kilogramo <-> Quintal

        /// <summary>
        /// Convierte kilogramos a quintales
        /// </summary>
        /// <param name="kilogramos">Valor en kilogramos como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult KilogramoAQuintal(string kilogramos)
        {
            try
            {
                return _masaBusinessService.ConvertirKilogramoAQuintal(kilogramos);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.KILOGRAMO,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte quintales a kilogramos
        /// </summary>
        /// <param name="quintales">Valor en quintales como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult QuintalAKilogramo(string quintales)
        {
            try
            {
                return _masaBusinessService.ConvertirQuintalAKilogramo(quintales);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.QUINTAL,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Kilogramo <-> Libra

        /// <summary>
        /// Convierte kilogramos a libras
        /// </summary>
        /// <param name="kilogramos">Valor en kilogramos como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult KilogramoALibra(string kilogramos)
        {
            try
            {
                return _masaBusinessService.ConvertirKilogramoALibra(kilogramos);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.KILOGRAMO,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte libras a kilogramos
        /// </summary>
        /// <param name="libras">Valor en libras como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult LibraAKilogramo(string libras)
        {
            try
            {
                return _masaBusinessService.ConvertirLibraAKilogramo(libras);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.LIBRA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion

        #region Conversiones Quintal <-> Libra

        /// <summary>
        /// Convierte quintales a libras
        /// </summary>
        /// <param name="quintales">Valor en quintales como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult QuintalALibra(string quintales)
        {
            try
            {
                return _masaBusinessService.ConvertirQuintalALibra(quintales);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.QUINTAL,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        /// <summary>
        /// Convierte libras a quintales
        /// </summary>
        /// <param name="libras">Valor en libras como string</param>
        /// <returns>ConversionResult con el resultado o error</returns>
        public ConversionResult LibraAQuintal(string libras)
        {
            try
            {
                return _masaBusinessService.ConvertirLibraAQuintal(libras);
            }
            catch (Exception ex)
            {
                var error = new ConversionError(
                    ErrorConstants.ERROR_INTERNO,
                    ErrorConstants.MSG_ERROR_INTERNO,
                    ErrorConstants.TIPO_SISTEMA,
                    null,
                    MasaConstants.LIBRA,
                    ex.Message
                );
                return ConversionResult.Fallo(error);
            }
        }

        #endregion
    }
}

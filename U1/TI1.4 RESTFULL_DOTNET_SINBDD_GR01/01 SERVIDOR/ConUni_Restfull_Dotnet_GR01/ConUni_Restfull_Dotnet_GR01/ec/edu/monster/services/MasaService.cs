using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.constants;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.models;
using ConUni_Restfull_Dotnet_GR01.ec.edu.monster.validators;

namespace ConUni_Restfull_Dotnet_GR01.ec.edu.monster.services
{
    /// <summary>
    /// Servicio para realizar conversiones de unidades de masa
    /// Soporta: Kilogramo, Quintal, Libra
    /// </summary>
    public class MasaService
    {
        #region Métodos con validación de string

        /// <summary>
        /// Convierte de Kilogramo a Quintal (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirKilogramoAQuintal(string kilogramosString)
        {
            var error = BaseValidator.ValidarStringPositivo(kilogramosString, MasaConstants.KILOGRAMO, out double kilogramos);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirKilogramoAQuintal(kilogramos);
        }

        /// <summary>
        /// Convierte de Quintal a Kilogramo (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirQuintalAKilogramo(string quintalesString)
        {
            var error = BaseValidator.ValidarStringPositivo(quintalesString, MasaConstants.QUINTAL, out double quintales);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirQuintalAKilogramo(quintales);
        }

        /// <summary>
        /// Convierte de Kilogramo a Libra (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirKilogramoALibra(string kilogramosString)
        {
            var error = BaseValidator.ValidarStringPositivo(kilogramosString, MasaConstants.KILOGRAMO, out double kilogramos);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirKilogramoALibra(kilogramos);
        }

        /// <summary>
        /// Convierte de Libra a Kilogramo (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirLibraAKilogramo(string librasString)
        {
            var error = BaseValidator.ValidarStringPositivo(librasString, MasaConstants.LIBRA, out double libras);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirLibraAKilogramo(libras);
        }

        /// <summary>
        /// Convierte de Quintal a Libra (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirQuintalALibra(string quintalesString)
        {
            var error = BaseValidator.ValidarStringPositivo(quintalesString, MasaConstants.QUINTAL, out double quintales);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirQuintalALibra(quintales);
        }

        /// <summary>
        /// Convierte de Libra a Quintal (con validación de string)
        /// </summary>
        public ConversionResultModel ConvertirLibraAQuintal(string librasString)
        {
            var error = BaseValidator.ValidarStringPositivo(librasString, MasaConstants.LIBRA, out double libras);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            return ConvertirLibraAQuintal(libras);
        }

        #endregion

        #region Métodos internos con double (para reutilización)

        /// <summary>
        /// Convierte de Kilogramo a Quintal
        /// </summary>
        private ConversionResultModel ConvertirKilogramoAQuintal(double kilogramos)
        {
            var error = BaseValidator.ValidarValorPositivo(kilogramos, MasaConstants.KILOGRAMO);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double quintales = kilogramos * MasaConstants.KILOGRAMO_A_QUINTAL;

            var resultado = new UnidadConversionModel(
                kilogramos,
                quintales,
                MasaConstants.KILOGRAMO,
                MasaConstants.QUINTAL,
                "Masa",
                MasaConstants.KILOGRAMO_A_QUINTAL
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Quintal a Kilogramo
        /// </summary>
        private ConversionResultModel ConvertirQuintalAKilogramo(double quintales)
        {
            var error = BaseValidator.ValidarValorPositivo(quintales, MasaConstants.QUINTAL);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double kilogramos = quintales * MasaConstants.QUINTAL_A_KILOGRAMO;

            var resultado = new UnidadConversionModel(
                quintales,
                kilogramos,
                MasaConstants.QUINTAL,
                MasaConstants.KILOGRAMO,
                "Masa",
                MasaConstants.QUINTAL_A_KILOGRAMO
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Kilogramo a Libra
        /// </summary>
        private ConversionResultModel ConvertirKilogramoALibra(double kilogramos)
        {
            var error = BaseValidator.ValidarValorPositivo(kilogramos, MasaConstants.KILOGRAMO);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double libras = kilogramos * MasaConstants.KILOGRAMO_A_LIBRA;

            var resultado = new UnidadConversionModel(
                kilogramos,
                libras,
                MasaConstants.KILOGRAMO,
                MasaConstants.LIBRA,
                "Masa",
                MasaConstants.KILOGRAMO_A_LIBRA
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Libra a Kilogramo
        /// </summary>
        private ConversionResultModel ConvertirLibraAKilogramo(double libras)
        {
            var error = BaseValidator.ValidarValorPositivo(libras, MasaConstants.LIBRA);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double kilogramos = libras * MasaConstants.LIBRA_A_KILOGRAMO;

            var resultado = new UnidadConversionModel(
                libras,
                kilogramos,
                MasaConstants.LIBRA,
                MasaConstants.KILOGRAMO,
                "Masa",
                MasaConstants.LIBRA_A_KILOGRAMO
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Quintal a Libra
        /// </summary>
        private ConversionResultModel ConvertirQuintalALibra(double quintales)
        {
            var error = BaseValidator.ValidarValorPositivo(quintales, MasaConstants.QUINTAL);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double libras = quintales * MasaConstants.QUINTAL_A_LIBRA;

            var resultado = new UnidadConversionModel(
                quintales,
                libras,
                MasaConstants.QUINTAL,
                MasaConstants.LIBRA,
                "Masa",
                MasaConstants.QUINTAL_A_LIBRA
            );

            return ConversionResultModel.Exito(resultado);
        }

        /// <summary>
        /// Convierte de Libra a Quintal
        /// </summary>
        private ConversionResultModel ConvertirLibraAQuintal(double libras)
        {
            var error = BaseValidator.ValidarValorPositivo(libras, MasaConstants.LIBRA);
            if (error != null)
                return ConversionResultModel.Fallo(error);

            double quintales = libras * MasaConstants.LIBRA_A_QUINTAL;

            var resultado = new UnidadConversionModel(
                libras,
                quintales,
                MasaConstants.LIBRA,
                MasaConstants.QUINTAL,
                "Masa",
                MasaConstants.LIBRA_A_QUINTAL
            );

            return ConversionResultModel.Exito(resultado);
        }

        #endregion
    }
}

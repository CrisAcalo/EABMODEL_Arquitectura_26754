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
        /// <summary>
        /// Convierte de Kilogramo a Quintal
        /// </summary>
        public ConversionResultModel ConvertirKilogramoAQuintal(double kilogramos)
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
        public ConversionResultModel ConvertirQuintalAKilogramo(double quintales)
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
        public ConversionResultModel ConvertirKilogramoALibra(double kilogramos)
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
        public ConversionResultModel ConvertirLibraAKilogramo(double libras)
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
        public ConversionResultModel ConvertirQuintalALibra(double quintales)
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
        public ConversionResultModel ConvertirLibraAQuintal(double libras)
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
    }
}

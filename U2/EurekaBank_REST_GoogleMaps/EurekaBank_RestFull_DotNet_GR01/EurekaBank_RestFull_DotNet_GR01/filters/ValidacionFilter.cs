using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using EurekaBank_RestFull_DotNet_GR01.Helpers;

namespace EurekaBank_RestFull_DotNet_GR01.Filters
{
    /// <summary>
    /// Filtro global para validaciones que estandariza las respuestas de error
    /// </summary>
    public class ValidacionFilter : ActionFilterAttribute
    {
        /// <summary>
        /// Se ejecuta antes de la acción del controlador
        /// </summary>
        /// <param name="context">Contexto de la acción</param>
        public override void OnActionExecuting(ActionExecutingContext context)
        {
            if (!context.ModelState.IsValid)
            {
                var respuestaError = RespuestaHelper.CrearErrorValidacion(context.ModelState);
                context.Result = new BadRequestObjectResult(respuestaError);
                return;
            }

            base.OnActionExecuting(context);
        }
    }
}
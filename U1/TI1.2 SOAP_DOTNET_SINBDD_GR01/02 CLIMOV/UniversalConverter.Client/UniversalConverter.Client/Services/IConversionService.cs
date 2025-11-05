using System;
using System.Collections.Generic;
using System.Text;
using UniversalConverter.Client.Models;

namespace UniversalConverter.Client.Services
{
    public interface IConversionService
    {
        // Un único método para realizar cualquier conversión
        Task<ConversionResponse> ConvertAsync(ConversionRequest request);
    }
}

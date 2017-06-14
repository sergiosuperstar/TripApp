using Microsoft.AspNetCore.Builder;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Api key auth extensions.
    /// </summary>
    public static class ApiKeyAuthenticationExtensions
    {
        /// <summary>
        /// Extensions for api key auth.
        /// </summary>
        /// <param name="app"></param>
        /// <returns></returns>
        public static IApplicationBuilder UseApiKeyAuthentication(this IApplicationBuilder app)
        {
            return app.UseMiddleware<ApiKeyAuthenticationMiddleware>();
        }
    }
}

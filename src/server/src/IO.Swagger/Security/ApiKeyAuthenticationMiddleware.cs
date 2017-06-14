using IO.Swagger.Data;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using System.Text.Encodings.Web;

namespace IO.Swagger.Security
{
    /// <summary>
    /// ApiKey middleware.
    /// </summary>
    public class ApiKeyAuthenticationMiddleware : AuthenticationMiddleware<ApiKeyAuthenticationOptions>
    {
        private IApiKeyValidator _validator;
        private readonly TripAppContext _context;
        /// <summary>
        /// ApiKey middleware constructor.
        /// </summary>
        /// <param name="validator"></param>
        /// <param name="next"></param>
        /// <param name="options"></param>
        /// <param name="loggerFactory"></param>
        /// <param name="encoder"></param>
        /// <param name="context"></param>
        public ApiKeyAuthenticationMiddleware(
           IApiKeyValidator validator,  // custom dependency
           RequestDelegate next,
           ApiKeyOptions options,
           ILoggerFactory loggerFactory,
           UrlEncoder encoder,
           TripAppContext context)
           : base(next, options, loggerFactory, encoder)
        {
            _validator = validator;
            _context = context;
        }

        /// <summary>
        /// Creates authentication handler instance.
        /// </summary>
        /// <returns></returns>
        protected override AuthenticationHandler<ApiKeyAuthenticationOptions> CreateHandler()
        {
            return new ApiKeyAuthenticationHandler(_validator, _context);
        }
    }
}

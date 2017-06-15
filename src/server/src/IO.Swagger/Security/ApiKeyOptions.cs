using Microsoft.Extensions.Options;
namespace IO.Swagger.Security
{
    /// <summary>
    /// Implements IOption for api key.
    /// </summary>
    public class ApiKeyOptions : IOptions<ApiKeyAuthenticationOptions>
    {
        ApiKeyAuthenticationOptions _options;

        /// <summary>
        /// Create new option.
        /// </summary>
        /// <param name="options"></param>
        public ApiKeyOptions(ApiKeyAuthenticationOptions options)
        {
            _options = options;
        }
        /// <summary>
        /// returns api key auth option.
        /// </summary>
        public ApiKeyAuthenticationOptions Value
        {
            get
            {
                return _options;
            }
        }
    }
}

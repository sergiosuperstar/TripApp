using Microsoft.AspNetCore.Builder;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Api Key authentication options
    /// </summary>
    public class ApiKeyAuthenticationOptions : AuthenticationOptions
    {
        /// <summary>
        /// Creates default apikey auth handler options.
        /// </summary>
        public ApiKeyAuthenticationOptions()
        {
            this.AuthenticationScheme = "apikey";
            this.AutomaticAuthenticate = true;
            this.AutomaticChallenge = true;
        }
        /// <summary>
        /// Default header name.
        /// </summary>
        public const string DefaultHeaderName = "Authorization";

        /// <summary>
        /// Gets or Sets default header name.
        /// </summary>
        public string HeaderName { get; set; } = DefaultHeaderName;
    }
}

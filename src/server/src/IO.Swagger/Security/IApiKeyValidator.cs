using IO.Swagger.Data;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Api key validator interface.
    /// </summary>
    public interface IApiKeyValidator
    {
        /// <summary>
        /// Validates API key
        /// </summary>
        /// <param name="apiKey">Api key to validate</param>
        /// <param name="context"></param>
        /// <returns>Validation result.</returns>
        bool Validate(string apiKey, TripAppContext context);
    }
}

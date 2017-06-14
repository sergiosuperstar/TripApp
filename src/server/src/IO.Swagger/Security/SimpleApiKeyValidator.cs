using IO.Swagger.Data;
using System.Linq;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Simple api key validator just checks if there is a header with LONG value.
    /// We guess it will be logged in user id.
    /// This is very simple implementation only for demo purpose!
    /// </summary>
    public class SimpleApiKeyValidator : IApiKeyValidator
    {
        /// <summary>
        /// Validates apiKey by checking if it is a long value.
        /// </summary>
        /// <param name="apiKey"></param>
        /// <param name="context"></param>
        /// <returns></returns>
        public bool Validate(string apiKey, TripAppContext context)
        {
            long userId;
            bool userExists = false;
            if (long.TryParse(apiKey, out userId))
            {
                userExists = context.Users.Any(u => u.Id == userId);
            }
            return userExists;
        }
    }
}

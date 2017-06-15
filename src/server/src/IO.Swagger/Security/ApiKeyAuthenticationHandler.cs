using IO.Swagger.Data;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Primitives;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Api key auth handler
    /// </summary>
    public class ApiKeyAuthenticationHandler : AuthenticationHandler<ApiKeyAuthenticationOptions>
    {
        private IApiKeyValidator _validator;
        private readonly TripAppContext _context;

        /// <summary>
        /// Construct ApiKey auth handler.
        /// </summary>
        /// <param name="validator">Validator instance</param>
        /// <param name="context"></param>
        public ApiKeyAuthenticationHandler(IApiKeyValidator validator, TripAppContext context)
        {
            _validator = validator;
            _context = context;
        }

        /// <summary>
        /// Handle authentication.
        /// </summary>
        /// <returns>Result.</returns>
        protected override Task<AuthenticateResult> HandleAuthenticateAsync()
        {
            StringValues headerValue;
            if (!Context.Request.Headers.TryGetValue(Options.HeaderName, out headerValue))
            {
                return Task.FromResult(AuthenticateResult.Fail("Missing or malformed 'Authorization' header."));
            }

            var apiKey = headerValue.First();
            if (!_validator.Validate(apiKey, _context))
            {
                return Task.FromResult(AuthenticateResult.Fail("Invalid apiKey."));
            }

            // success! Now we just need to create the auth ticket
            var identity = new ClaimsIdentity("apikey"); // the name of our auth scheme
            
            var user = _context.Users.First(u => u.Id == long.Parse(apiKey));
            identity.AddClaim(new Claim(ClaimTypes.NameIdentifier, apiKey));
            identity.AddClaim(new Claim(ClaimTypes.Name, user.Username));
            identity.AddClaim(new Claim(ClaimTypes.Role, user.Role));
            identity.AddClaim(new Claim(ClaimTypes.GivenName, user.FirstName));
            identity.AddClaim(new Claim(ClaimTypes.Surname, user.LastName));


            var ticket = new AuthenticationTicket(new ClaimsPrincipal(identity), null, "apikey");
            return Task.FromResult(AuthenticateResult.Success(ticket));
        }
    }
}

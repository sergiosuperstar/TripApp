using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc.Authorization;
using Swashbuckle.Swagger.Model;
using Swashbuckle.SwaggerGen.Generator;
using System.Linq;

namespace IO.Swagger.Security
{
    /// <summary>
    /// Extend swashbuckle UI with apikey support
    /// </summary>
    public class ApiKeyOperationFilter : IOperationFilter
    {
        /// <summary>
        /// Constructs api key operation filter
        /// </summary>
        /// <param name="operation"></param>
        /// <param name="context"></param>
        public void Apply(Operation operation, OperationFilterContext context)
        {
            var actionName = context.ApiDescription.ActionDescriptor.DisplayName;
            var filterPipeline = context.ApiDescription.ActionDescriptor.FilterDescriptors;
            var isAuthorized = filterPipeline.Any(f => f.Filter is AuthorizeFilter 
                                                && (f.Filter as AuthorizeFilter).AuthorizeData.Count() == 1);
            var allowAnonymous = context.ApiDescription.GetActionAttributes().Any(a => a.GetType() == typeof(AllowAnonymousAttribute));

            if (isAuthorized && !allowAnonymous)
            {
                
                operation.Parameters.Add(new NonBodyParameter
                {
                    Name = "Authorization",
                    In = "header",
                    Description = "access token",
                    Required = true,
                    Type = "string"
                });
            }

        }
    }
}

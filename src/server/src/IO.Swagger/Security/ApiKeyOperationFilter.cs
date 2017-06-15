using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc.Authorization;
using Swashbuckle.Swagger.Model;
using Swashbuckle.SwaggerGen.Generator;
using System.Collections.Generic;
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
                if (operation.Parameters == null)
                {
                    operation.Parameters = new List<IParameter>();
                }

                operation.Parameters.Add(new NonBodyParameter
                {
                    Name = "Authorization",
                    In = "header",
                    Description = "Custom access token / api key",
                    Required = true,
                    Type = "string"
                });
            }

        }
    }
}

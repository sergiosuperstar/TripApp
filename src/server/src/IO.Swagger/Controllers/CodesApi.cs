/*
 * Simple TripApp API
 *
 * This is a simple TripApp API
 *
 * OpenAPI spec version: 1.0.0
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Swashbuckle.SwaggerGen.Annotations;
using IO.Swagger.Models;
using IO.Swagger.Data;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using IO.Swagger.Logging;
using Microsoft.Extensions.Logging;

namespace IO.Swagger.Controllers
{
    /// <summary>
    /// Operations for purchase codes manipulation
    /// </summary>
    public class CodesApiController : Controller
    {
        private readonly TripAppContext _context;
        private readonly ILogger _logger;

        /// <summary>
        /// Initializes controller.
        /// </summary>
        /// <param name="context">Db context to use.</param>
        public CodesApiController(TripAppContext context, ILogger<CodesApiController> logger)
        {
            _context = context;
            _logger = logger;
        }

        /// <summary>
        /// adds a purchase code
        /// </summary>
        /// <remarks>Adds an item to the system</remarks>
        /// <param name="purchaseCode">PurchaseCode item to add</param>
        /// <response code="201">item created</response>
        /// <response code="400">invalid input, object invalid</response>
        /// <response code="409">item already exists</response>
        [HttpPost]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/codes")]
        [SwaggerOperation("AddPurchaseCode")]
        public virtual IActionResult AddPurchaseCode([FromBody]PurchaseCode purchaseCode)
        {
            // TODO ftn: Add validation to the purchaseCode parameter!!!
            // Return 400 - BadRequest if not valid!
            if (_context.Codes.FirstOrDefault(c => c.Code == purchaseCode.Code) != null)
            {
                return StatusCode(StatusCodes.Status409Conflict, purchaseCode); // 409 already exists!
            }

            try
            {
                _context.Codes.Add(purchaseCode);
                _context.SaveChanges();
                return Created(Request.Host.ToString(), purchaseCode); // 201 Created successfuly.
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.INSERT_ITEM, "AddPurchaseCode({purchaseCode}) NOT ADDED", purchaseCode);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }

        }

        /// <summary>
        /// searches purchase codes for specified user
        /// </summary>
        /// <remarks>By passing in the appropriate options, you can search for purchase codes that were used by user </remarks>
        /// <param name="userId">pass an user id for desired user</param>
        /// <response code="200">search results matching criteria</response>
        /// <response code="400">bad input parameter</response>
        [HttpGet]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/codes/user")]
        [SwaggerOperation("SearchCodesForUser")]
        [SwaggerResponse(200, type: typeof(List<PurchaseCode>))]
        public virtual IActionResult SearchCodesForUser([FromQuery]string userId)
        {
            int id;
            if (!int.TryParse(userId, out id))
            {
                return StatusCode(StatusCodes.Status400BadRequest);
            }
            try
            {
                var codes = _context.Codes.Where(c => c.User.Id == id).ToList();
                return new ObjectResult(codes);
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.LIST_ITEMS, "SearchCodes({userId}) NOT FOUND", userId);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }

        }

        /// <summary>
        /// searches purchase codes
        /// </summary>
        /// <remarks>By passing in the appropriate options, you can search for available purchase codes in the system </remarks>
        /// <param name="searchString">pass an optional search string for looking up purchase codes</param>
        /// <param name="skip">number of records to skip for pagination</param>
        /// <param name="limit">maximum number of records to return</param>
        /// <response code="200">search results matching criteria</response>
        /// <response code="400">bad input parameter</response>
        [HttpGet]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/codes")]
        [SwaggerOperation("SearchCodes")]
        [SwaggerResponse(200, type: typeof(List<PurchaseCode>))]
        public virtual IActionResult SearchCodes([FromQuery]string searchString, [FromQuery]int? skip, [FromQuery]int? limit)
        {
            int id;
            if (!int.TryParse(searchString, out id))
            {
                return StatusCode(StatusCodes.Status400BadRequest);
            }
            try
            {
                var codes = _context.Codes.Where(c => c.Id == id).ToList();
                return new ObjectResult(codes);
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.LIST_ITEMS, "SearchCodes({searchString}) NOT FOUND", searchString);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }

        }

        /// <summary>
        /// updates a purchase code
        /// </summary>
        /// <remarks>Updates an item to the system</remarks>
        /// <param name="purchaseCode">PurchaseCode item to update</param>
        /// <response code="200">item updated</response>
        /// <response code="400">invalid input, object invalid</response>
        /// <response code="404">invalid input, object not found</response>
        [HttpPut]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/codes")]
        [SwaggerOperation("UpdatePurchaseCode")]
        public virtual IActionResult UpdatePurchaseCode([FromBody]PurchaseCode purchaseCode)
        {
            // TODO ftn: Add validation to the purchaseCode parameter!!!
            // Return 400 - BadRequest if not valid!
            PurchaseCode code = _context.Codes.FirstOrDefault(c => c.Code == purchaseCode.Code);
            if (code == null)
            {
                //return StatusCode(StatusCodes.Status404NotFound, purchaseCode); // 400 not found!
                return new ObjectResult(false);
            }

            if (code.Used == null)
            {
                return new ObjectResult(false);
            }

            if ((bool)code.Used)
            {
                return new ObjectResult(false);
            }

            try
            {
                code.Used = true;
                code.User = _context.Users.FirstOrDefault(u => u.Username == purchaseCode.User.Username);
                code.UsageDateTime = DateTime.Now;
                code.User.Balance += code.Value;
                _context.Entry(code).State = EntityState.Modified;
                _context.Entry(code.User).State = EntityState.Unchanged;
                _context.SaveChanges();
                return new ObjectResult(true);
                //return Ok(purchaseCode);
            }
            catch (Exception)
            {
                return StatusCode(StatusCodes.Status500InternalServerError);
            }
        }
    }
}

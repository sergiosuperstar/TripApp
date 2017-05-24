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
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using IO.Swagger.Logging;

namespace IO.Swagger.Controllers
{
    /// <summary>
    /// 
    /// </summary>
    public class TicketsApiController : Controller
    {
        private readonly TripAppContext _context;
        private readonly IConfiguration _configuration;
        private readonly ILogger _logger;

        public TicketsApiController(TripAppContext context, IConfiguration configuration, ILogger<TicketsApiController> logger)
        {
            _context = context;
            _configuration = configuration;
            _logger = logger;
        }

        /// <summary>
        /// adds an ticket purchase item
        /// </summary>
        /// <remarks>Adds an item to the system</remarks>
        /// <param name="ticketPurchase">TicketPurchase item to add</param>
        /// <response code="201">item created</response>
        /// <response code="400">invalid input, object invalid</response>
        /// <response code="409">item already exists</response>
        [HttpPost]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/tickets")]
        [SwaggerOperation("AddTicketPurchase")]
        public virtual IActionResult AddTicketPurchase([FromBody]TicketPurchase ticketPurchase)
        {
            var hasTypeAndUser = ticketPurchase.TypeId != null
                                && ticketPurchase.TypeId > 0
                                && ticketPurchase.UserId != null
                                && ticketPurchase.UserId > 0;
            if (!hasTypeAndUser)
            {
                return StatusCode(StatusCodes.Status400BadRequest, ticketPurchase);
            }

            try
            {
                //TODO: skinuti useru novac ili vratiti gresku ako nema dovoljno
                var type = _context.Types.First(t => t.Id == ticketPurchase.TypeId);
                ticketPurchase.Code = Guid.NewGuid();
                ticketPurchase.StartDateTime = DateTime.Now.AddMinutes(_configuration.GetSection(Startup.AppSettingsConfigurationSectionKey).GetValue<int>(Startup.AppSettingsMinutesUntilTicketStartKey));
                ticketPurchase.EndDateTime = DateTime.Now.AddMinutes(type.Duration.Value*60 + _configuration.GetSection(Startup.AppSettingsConfigurationSectionKey).GetValue<int>(Startup.AppSettingsMinutesUntilTicketStartKey));
                ticketPurchase.Price = type.Price;

                _context.Purchases.Add(ticketPurchase);
                _context.SaveChanges();
                return StatusCode(StatusCodes.Status201Created, ticketPurchase);
            }catch(Exception)
            {
                _logger.LogError(LoggingEvents.INSERT_ITEM, "AddTicketPurchase({ticketPurchase}) NOT ADDED", ticketPurchase);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }
        }


        /// <summary>
        /// searches tickets purchases
        /// </summary>
        /// <remarks>By passing in the appropriate options, you can search for available ticket in the system </remarks>
        /// <param name="searchString">pass an optional search string for looking up tickets</param>
        /// <param name="skip">number of records to skip for pagination</param>
        /// <param name="limit">maximum number of records to return</param>
        /// <response code="200">search results matching criteria</response>
        /// <response code="400">bad input parameter</response>
        [HttpGet]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/tickets")]
        [SwaggerOperation("SearchTickets")]
        [SwaggerResponse(200, type: typeof(List<TicketPurchase>))]
        public virtual IActionResult SearchTickets([FromQuery]string searchString, [FromQuery]int? skip, [FromQuery]int? limit)
        {
            int id;
            var searchItems = searchString.Split(':');
            if (searchItems.Length != 2)
            {
                return StatusCode(StatusCodes.Status400BadRequest, searchString);
            }
            var searchBy = searchItems[0];
            var searchValue = searchItems[1];

            if (searchBy != "all" && searchBy != "id")
            {
                return StatusCode(StatusCodes.Status400BadRequest, searchBy);
            }

            

            if (!int.TryParse(searchValue, out id))
            {
                return StatusCode(StatusCodes.Status400BadRequest);
            }
            try
            {
                List<TicketPurchase> purchases;
                if (searchBy == "id") { 
                    purchases = _context.Purchases.Include(t => t.Type).Include(u => u.User).Where(c => c.Id == id).ToList();
                }
                else
                {
                    purchases = _context.Purchases.Include(t => t.Type).Include(u => u.User).Where(c => c.UserId == id).ToList();
                }
                return new ObjectResult(purchases);
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.LIST_ITEMS, "SearchTickets({searchString}) NOT FOUND", searchString);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }
        }
    }
}

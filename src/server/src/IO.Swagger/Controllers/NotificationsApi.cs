using IO.Swagger.Data;
using IO.Swagger.Logging;
using IO.Swagger.Models;
using IO.Swagger.Notifications;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Swashbuckle.SwaggerGen.Annotations;
using System;
using System.Net.Http;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.Controllers
{
    /// <summary>
    /// Notifications controller
    /// </summary>
    public class NotificationsApi : Controller
    {

        private readonly TripAppContext _context;
        private readonly IConfiguration _configuration;
        private readonly ILogger _logger;
        private readonly FCMNotificationService _notificationService;


        /// <summary>
        /// Notifications controller constructor.
        /// </summary>
        /// <param name="context"></param>
        /// <param name="configuration"></param>
        /// <param name="logger"></param>
        /// <param name="notificationService"></param>
        public NotificationsApi(TripAppContext context, IConfiguration configuration, ILogger<TicketsApiController> logger, FCMNotificationService notificationService)
        {
            _context = context;
            _configuration = configuration;
            _logger = logger;
            _notificationService = notificationService;
        }

        /// <summary>
        /// Sends news to all devices
        /// </summary>
        /// <remarks>Sends a notification</remarks>
        /// <param name="notification">Notification item to send</param>
        /// <response code="200">Sent</response>
        /// <response code="400">invalid input, object invalid</response>
        [HttpPost]
        [Route("/sergiosuperstar/TripAppSimple/1.0.0/notifications")]
        [SwaggerOperation("Send")]
        [Authorize(ActiveAuthenticationSchemes = "apikey")]
        public virtual async Task<IActionResult> Send([FromBody]Notification notification)
        {
            // TODO FTN: Add validation!
            var loggedInUserId = long.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);


            var hasValidTopic = notification != null
                                && notification.Topic == "news";

            if (!hasValidTopic)
            {
                return StatusCode(StatusCodes.Status400BadRequest, notification);
            }

            try
            {
                var result = await _notificationService.Send(notification);
                return Ok();
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.INSERT_ITEM, $"Send({notification}) FAILED.", notification);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }
        }

    }
}

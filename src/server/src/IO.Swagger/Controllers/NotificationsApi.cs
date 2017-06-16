using IO.Swagger.Data;
using IO.Swagger.Logging;
using IO.Swagger.Models;
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
        private const string API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
        private const string TRIPAPP_FCM_SERVER_API_KEY = "AAAAjdb4JLs:APA91bHgPgB4kCNiYJgTf45eYDRk6hmzSFYRmneaqFHxDVwss2QX9B4iD4WucB5bGXyEluq5H0ZrADtIyM-xFrv9jYszb2mVV3kwDOxyesEkYsV2aLVqFN1LMaJXy77QFwU97zq5wb89";
        private const string TRIPAPP_FCM_SENDER_ID = "609196975291";

        private readonly TripAppContext _context;
        private readonly IConfiguration _configuration;
        private readonly ILogger _logger;


        /// <summary>
        /// Notifications controller constructor.
        /// </summary>
        /// <param name="context"></param>
        /// <param name="configuration"></param>
        /// <param name="logger"></param>
        public NotificationsApi(TripAppContext context, IConfiguration configuration, ILogger<TicketsApiController> logger)
        {
            _context = context;
            _configuration = configuration;
            _logger = logger;
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
                var result = await SendNotification(notification);
                return Ok();
            }
            catch (Exception)
            {
                _logger.LogError(LoggingEvents.INSERT_ITEM, $"Send({notification}) FAILED.", notification);
                return StatusCode(StatusCodes.Status500InternalServerError);
            }
        }

        private async Task<bool> SendNotification(Notification notification)
        {
            //FCMPushNotification result = new FCMPushNotification();
            try
            {
                //result.Successful = true;
                //result.Error = null;
                // var value = message;
                var client = new HttpClient();
                client.DefaultRequestHeaders.TryAddWithoutValidation("Authorization", string.Format("key={0}", TRIPAPP_FCM_SERVER_API_KEY));
                // client.DefaultRequestHeaders.TryAddWithoutValidation("Sender", string.Format("key={0}", TRIPAPP_FCM_SENDER_ID));
                
                var message = new
                {
                    // to = YOUR_FCM_DEVICE_ID, // Uncoment this if you want to test for single device
                    to = "/topics/" + notification.Topic, // this is for topic 
                    notification = new
                    {
                        title = notification.Title,
                        body = notification.Message,
                        //icon="myicon"
                    }
                };

                var json = JsonConvert.SerializeObject(message);

                var result = await client.PostAsync(API_URL_FCM, 
                    new StringContent(json, Encoding.UTF8, @"application/json"));
            }
            catch (Exception ex)
            {
                throw;
            }
            return true;
        }

    }
}

using IO.Swagger.Logging;
using IO.Swagger.Models;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace IO.Swagger.Notifications
{
    /// <summary>
    /// Helper used to send notifications via firebase cloud messaging.
    /// </summary>
    public class FCMNotificationService
    {
        private const string API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
        private const string TRIPAPP_FCM_SERVER_API_KEY = "AAAAjdb4JLs:APA91bHgPgB4kCNiYJgTf45eYDRk6hmzSFYRmneaqFHxDVwss2QX9B4iD4WucB5bGXyEluq5H0ZrADtIyM-xFrv9jYszb2mVV3kwDOxyesEkYsV2aLVqFN1LMaJXy77QFwU97zq5wb89";
        private const string TRIPAPP_FCM_SENDER_ID = "609196975291";

        private readonly ILogger _logger;

        /// <summary>
        /// Instanciate notification service.
        /// </summary>
        /// <param name="logger"></param>
        public FCMNotificationService(ILogger<FCMNotificationService> logger)
        {
            _logger = logger;
        }

        /// <summary>
        /// Send notification
        /// </summary>
        /// <param name="notification"></param>
        /// <returns></returns>
        public async Task<bool> Send(Notification notification)
        {
            bool success = false;
            try
            {
                var client = new HttpClient();
                client.DefaultRequestHeaders.TryAddWithoutValidation("Authorization", string.Format("key={0}", TRIPAPP_FCM_SERVER_API_KEY));
                // client.DefaultRequestHeaders.TryAddWithoutValidation("Sender", string.Format("key={0}", TRIPAPP_FCM_SENDER_ID));

                var toWhom = string.IsNullOrEmpty(notification.DeviceID) ? "/topics/" + notification.Topic : notification.DeviceID;

                var message = new
                {
                    // to = YOUR_FCM_DEVICE_ID, // Uncoment this if you want to test for single device
                    // to = "/topics/" + notification.Topic, // this is for topic 
                    to = toWhom,
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
                success = true;
            }
            catch (Exception ex)
            {
                _logger.LogError(LoggingEvents.INSERT_ITEM, $"Send({notification}) NOT sent.", notification);
                // WHATEVER HAPPEN WE DO NOT BREAK WITH EXCEPTION AS WE DO NOT CARE IF MESSAGING WORKED!
                // WE JUST RETURN FALSE!
                success = false;
            }
            return success;
        }

    }
}

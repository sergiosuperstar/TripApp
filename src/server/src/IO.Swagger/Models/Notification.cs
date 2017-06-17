using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;

namespace IO.Swagger.Models
{
    /// <summary>
    /// Push Notification Details
    /// </summary>
    [DataContract]
    public partial class Notification
    {
        /// <summary>
        /// Title
        /// </summary>
        [DataMember]
        public string Title { get; set; }

        /// <summary>
        /// Message
        /// </summary>
        [DataMember]
        public string Message { get; set; }

        /// <summary>
        /// Topic
        /// </summary>
        [DataMember]
        public string Topic { get; set; }

        /// <summary>
        /// Device Id
        /// </summary>
        [DataMember]
        public string DeviceID { get; set; }

    }
}

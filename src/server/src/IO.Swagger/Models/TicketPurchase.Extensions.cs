using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;

namespace IO.Swagger.Models
{
    /// <summary>
    /// Operations for tickets purchases manipulation
    /// </summary>
    public partial class TicketPurchase
    {
        /// <summary>
        /// Default constructor.
        /// </summary>
        public TicketPurchase()
        {
        }

        /// <summary>
        /// Gets or Sets User Id
        /// </summary>
        [DataMember(Name = "userId")]
        [ForeignKey("User")]
        public long? UserId { get; set; }

        /// <summary>
        /// Gets or Sets Type Id
        /// </summary>
        [DataMember(Name = "typeId")]
        [ForeignKey("Type")]
        public int? TypeId { get; set; }
    }
}

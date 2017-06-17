using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.Serialization;
using System.Threading.Tasks;

namespace IO.Swagger.Models
{
    /// <summary>
    /// Operations for purchase codes manipulation
    /// </summary>
    public partial class PurchaseCode
    {
        /// <summary>
        /// Default constructor.
        /// </summary>
        public PurchaseCode()
        {
        }

        /// <summary>
        /// Gets or Sets User Id
        /// </summary>
        [DataMember(Name = "userId")]
        [ForeignKey("User")]
        public long? UserId { get; set; }
    }
}

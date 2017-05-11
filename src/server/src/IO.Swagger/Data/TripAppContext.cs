using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace IO.Swagger.Data
{
    public class TripAppContext : DbContext
    {
        public TripAppContext(DbContextOptions<TripAppContext> options): base(options)
        {
        }
        public DbSet<Models.User> Users { get; set; }
        public DbSet<Models.TicketType> Types { get; set; }
        public DbSet<Models.TicketValidation> Validations { get; set; }
        public DbSet<Models.TicketPurchase> Purchases { get; set; }
        public DbSet<Models.PurchaseCode> Codes { get; set; }

    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using IO.Swagger.Models;

namespace IO.Swagger.Data
{
    /// <summary>
    /// Initialize db data via seed methods.
    /// </summary>
    public class TripAppDbInitializer
    {
        /// <summary>
        /// Seed database with predefined data.
        /// </summary>
        /// <param name="context">Context to use for saving seeded data.</param>
        public static void Seed(TripAppContext context)
        {
            // Users:
            User admin = new User(1,"administrator","Pera", "Administratovic","admin@tripapp.com", "Pa$$w0rd", "333-123", "administrator", 2000.0d);
            context.Users.Add(admin);

            User passenger = new User(2, "darinka.putnik", "Darinka", "Putnik", "daracar@tripapp.com", "Pa$$w0rd", "333-444", "passenger", 1000.0d);
            context.Users.Add(passenger);

            User controller = new User(3, "milos.nagib", "Milos", "Nagib", "mrgud@tripapp.com", "Pa$$w0rd", "333-555", "controller", 3000.0d);
            context.Users.Add(controller);

            // Purchase codes:
            PurchaseCode code = new PurchaseCode(1, Guid.NewGuid(), 500.0d, DateTime.Now, null, false, null);
            context.Codes.Add(code);

            // Ticket types:
            TicketType typeHour = new TicketType(1, "hour", 60, 30.0d);
            TicketType typeDay = new TicketType(1, "day", 60*24, 30.0d * 12d);

            // Purchases:
            TicketPurchase purchase = new TicketPurchase(1, Guid.NewGuid(), 30.0d, DateTime.Now, DateTime.Now.AddMinutes(typeHour.Duration.Value), 1,typeHour, passenger);

            // Validations:
            TicketValidation validation = new TicketValidation(1,DateTime.Now.AddMinutes(2), true, typeHour, controller);

            context.SaveChanges();
        }
    }
}

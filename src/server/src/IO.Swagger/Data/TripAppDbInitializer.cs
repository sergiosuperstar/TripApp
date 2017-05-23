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
            string str = Hash.sha256("pa$$w0rd" + "p0mpeja");

            // Users:
            User admin = new User(1,"administrator", "Pera", "Administratovic","admin@tripapp.com", str, "333-123", "administrator", 2000.0d);
            context.Users.Add(admin);

            User passenger = new User(2, "darinka.putnik", "Darinka", "Putnik", "daracar@tripapp.com", str, "333-444", "passenger", 1000.0d);
            context.Users.Add(passenger);

            User controller = new User(3, "milos.nagib", "Milos", "Nagib", "mrgud@tripapp.com", str, "333-555", "controller", 3000.0d);
            context.Users.Add(controller);

            // Purchase codes:
            PurchaseCode code = new PurchaseCode(1, Guid.NewGuid(), 500.0d, DateTime.Now, null, false, null);
            context.Codes.Add(code);

            // Ticket types:
            TicketType typeHour = new TicketType(1, "hourly", 1, 1.2d);
            TicketType typeDay = new TicketType(2, "daily", 24, 5d);
            TicketType typeMonth = new TicketType(3, "monthly", 24*30, 40d);
            TicketType typeYear = new TicketType(4, "yearly", 24*365, 200d);

            // Purchases:
            TicketPurchase purchase = new TicketPurchase(1, Guid.NewGuid(), 30.0d, DateTime.Now, DateTime.Now.AddMinutes(typeHour.Duration.Value), 1,typeHour, passenger);

            // Validations:
            TicketValidation validation = new TicketValidation(1,DateTime.Now.AddMinutes(2), true, typeHour, controller);

            context.SaveChanges();
        }
    }
}

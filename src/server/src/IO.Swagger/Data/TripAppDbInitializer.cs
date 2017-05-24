using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using IO.Swagger.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.DependencyInjection;

namespace IO.Swagger.Data
{
    /// <summary>
    /// Initialize db data via seed methods.
    /// </summary>
    public class TripAppDbInitializer
    {
        private const string defaultPassword = "Pa$$w0rd";
        private const string devPassword = "dev";
        /// <summary>
        /// Seed database with predefined data.
        /// </summary>
        /// <param name="serviceProvider">DI service provider instance.</param>
        public static void Seed(IServiceProvider serviceProvider)
        {
            var context = serviceProvider.GetService<TripAppContext>();
            var hasher = serviceProvider.GetService<IPasswordHasher<User>>();
            
            string defaultPass = hasher.HashPassword(null, defaultPassword);
            string devPass = hasher.HashPassword(null, devPassword);
            
            // Users:
            User admin = new User(1,"administrator", "Pera", "Administratovic","admin@tripapp.com", defaultPass, "333-123", "administrator", 2000.0d);
            context.Users.Add(admin);

            User passenger = new User(2, "darinka.putnik", "Darinka", "Putnik", "daracar@tripapp.com", defaultPass, "333-444", "passenger", 1000.0d);
            context.Users.Add(passenger);

            User controller = new User(3, "milos.nagib", "Milos", "Nagib", "mrgud@tripapp.com", defaultPass, "333-555", "controller", 3000.0d);
            context.Users.Add(controller);

            User devadmin = new User(4, "dev", "Bill", "Linux Idol", "billy@tripapp.com", devPass, "123-456", "administrator", 5000.0d);
            context.Users.Add(devadmin);

            // Purchase codes:
            PurchaseCode code = new PurchaseCode(1, Guid.NewGuid(), 500.0d, DateTime.Now, null, false, null);
            context.Codes.Add(code);

            // Ticket types:
            TicketType typeHour = new TicketType(1, "Hourly ticket", 1, 30d);
            TicketType typeDay = new TicketType(2, "Daily ticket", 24, 30*5d);
            TicketType typeMonth = new TicketType(3, "Monthly ticket", 24*30, 30*5*21d);
            TicketType typeYear = new TicketType(4, "Yearly ticket", 24*365, 30*5*200d);
            context.Types.Add(typeHour);
            context.Types.Add(typeDay);
            context.Types.Add(typeMonth);
            context.Types.Add(typeYear);
            
            // Purchases:
            TicketPurchase purchase = new TicketPurchase(1, Guid.NewGuid(), 30.0d, DateTime.Now, DateTime.Now.AddMinutes(typeHour.Duration.Value), 1,typeHour, passenger);
            context.Purchases.Add(purchase);
            
            // Validations:
            TicketValidation validation = new TicketValidation(1,DateTime.Now.AddMinutes(2), true, typeHour, controller);
            context.Validations.Add(validation);

            context.SaveChanges();
        }
    }
}

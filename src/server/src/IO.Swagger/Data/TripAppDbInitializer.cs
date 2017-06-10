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
        private const string defaultPassword = "a";
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
            User admin = new User(1,"admin", "Pera", "Administratovic","admin@tripapp.com", defaultPass, "333-123", "administrator", 2000.0d);
            context.Users.Add(admin);

            User passenger = new User(2, "a", "Darinka", "Putnik", "daracar@tripapp.com", defaultPass, "333-444", "passenger", 1000.0d);
            context.Users.Add(passenger);

            User controller = new User(3, "ctrl", "Milos", "Nagib", "mrgud@tripapp.com", defaultPass, "333-555", "controller", 3000.0d);
            context.Users.Add(controller);

            User devadmin = new User(4, "dev", "Bill", "Linux Idol", "billy@tripapp.com", devPass, "123-456", "administrator", 5000.0d);
            context.Users.Add(devadmin);

            // Purchase codes:
            PurchaseCode code = new PurchaseCode(1, Guid.Parse("bc495959-9aa7-447d-905d-0dfc74c16188"), 500.0d, DateTime.Now, null, false, null);
            PurchaseCode code2 = new PurchaseCode(2, Guid.Parse("e1f80425-7f55-4a2a-b777-f6833c1758ae"), 5.0d, DateTime.Now, null, false, null);
            PurchaseCode code3 = new PurchaseCode(3, Guid.Parse("33994bf3-0489-4897-9b87-853c76124ee1"), 30.0d, DateTime.Now, null, false, null);
            PurchaseCode code4 = new PurchaseCode(4, Guid.Parse("6d043fbc-5fdc-40a0-9cbb-58bf7aef1744"), 10.0d, DateTime.Now, null, false, null);
            PurchaseCode code5 = new PurchaseCode(5, Guid.Parse("6d043fac-5fdc-40a0-9cbb-58bf7aef1741"), 100.0d, DateTime.Now, null, false, null);
            context.Codes.Add(code);

            // Ticket types:
            TicketType typeHour = new TicketType(1, "Hourly ticket", 1, 1.2d);
            TicketType typeDay = new TicketType(2, "Daily ticket", 24, 5d);
            TicketType typeMonth = new TicketType(4, "Monthly ticket", 24*30, 40d);
            TicketType typeWeek = new TicketType(3, "Weekly ticket", 24*7, 16d);
            context.Types.Add(typeHour);
            context.Types.Add(typeDay);
            context.Types.Add(typeMonth);
            context.Types.Add(typeWeek);
            
            // Purchases:
            TicketPurchase purchase = new TicketPurchase(750, Guid.NewGuid(), 30.0d, DateTime.Now, DateTime.Now.AddMinutes(typeHour.Duration.Value*60), 1,typeHour, passenger);
            TicketPurchase purchase2 = new TicketPurchase(751, Guid.NewGuid(), 30.0d, DateTime.Now.AddHours(-3), DateTime.Now.AddHours(-2), 2, typeHour, passenger);
            TicketPurchase purchase3 = new TicketPurchase(752, Guid.NewGuid(), 30.0d, DateTime.Now.AddHours(-12), DateTime.Now.AddHours(12), 1, typeDay, passenger);
            context.Purchases.Add(purchase);
            context.Purchases.Add(purchase2);
            context.Purchases.Add(purchase3);

            // Validations:
            TicketValidation validation = new TicketValidation(1,DateTime.Now.AddMinutes(2), true, purchase, controller);
            context.Validations.Add(validation);

            context.SaveChanges();
        }
    }
}

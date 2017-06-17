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
            User admin = new User(1,"admin", "Pera", "Administratovic","admin@tripapp.com", defaultPass, "333-123", "administrator", 20.0d);
            context.Users.Add(admin);

            User passenger = new User(2, "a", "Darinka", "Putnik", "daracar@tripapp.com", defaultPass, "333-444", "passenger", 25.0d);
            context.Users.Add(passenger);

            User controller = new User(3, "ctrl", "Milos", "Nagib", "mrgud@tripapp.com", defaultPass, "333-555", "controller", 50.0d);
            context.Users.Add(controller);

            User devadmin = new User(4, "dev", "Bill", "Linux Idol", "billy@tripapp.com", devPass, "123-456", "administrator", 100.0d);
            context.Users.Add(devadmin);

            // Purchase codes:
            PurchaseCode code = new PurchaseCode(1, Guid.Parse("bc495959-9aa7-447d-905d-0dfc74c16188"), 5.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code2 = new PurchaseCode(2, Guid.Parse("e1f80425-7f55-4a2a-b777-f6833c1758ae"), 10.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code3 = new PurchaseCode(3, Guid.Parse("33994bf3-0489-4897-9b87-853c76124ee1"), 25.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code4 = new PurchaseCode(4, Guid.Parse("6d043fbc-5fdc-40a0-9cbb-58bf7aef1744"), 50.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code5 = new PurchaseCode(5, Guid.Parse("6d043fac-5fdc-40a0-9cbb-58bf7aef1741"), 100.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code6 = new PurchaseCode(6, Guid.Parse("d6b11c76-a0f5-4ebf-b840-6d1a7d1b73ba"), 5.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code7 = new PurchaseCode(7, Guid.Parse("ec61f4c7-3f0d-4754-ab27-8756efae6542"), 10.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code8 = new PurchaseCode(8, Guid.Parse("39d3e085-5243-4ef3-a619-7d9765329f29"), 10.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code9 = new PurchaseCode(9, Guid.Parse("4806c7e3-2c2f-4581-b8c1-b82537098ad4"), 10.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            PurchaseCode code10 = new PurchaseCode(10, Guid.Parse("9999c59f-5827-47f6-a837-d6c6c70ed7a9"), 10.0d, DateTime.Now.ToUniversalTime(), null, false, null);
            context.Codes.Add(code);
            context.Codes.Add(code2);
            context.Codes.Add(code3);
            context.Codes.Add(code4);
            context.Codes.Add(code5);
            context.Codes.Add(code6);
            context.Codes.Add(code7);
            context.Codes.Add(code8);
            context.Codes.Add(code9);
            context.Codes.Add(code10);


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
            purchase.UserId = passenger.Id;
            TicketPurchase purchase2 = new TicketPurchase(751, Guid.NewGuid(), 30.0d, DateTime.Now.AddHours(-3), DateTime.Now.AddHours(-2), 2, typeHour, passenger);
            purchase2.UserId = passenger.Id;
            TicketPurchase purchase3 = new TicketPurchase(752, Guid.NewGuid(), 30.0d, DateTime.Now.AddHours(-12), DateTime.Now.AddHours(12), 1, typeDay, passenger);
            purchase3.UserId = passenger.Id;
            TicketPurchase purchase4 = new TicketPurchase(753, Guid.NewGuid(), 30.0d, DateTime.Now, DateTime.Now.AddHours(24*30), 1, typeMonth, passenger);
            purchase4.UserId = passenger.Id;

            context.Purchases.Add(purchase);
            context.Purchases.Add(purchase2);
            context.Purchases.Add(purchase3);
            context.Purchases.Add(purchase4);

            // Validations:
            TicketValidation validation = new TicketValidation(1,DateTime.Now.AddMinutes(2).ToUniversalTime(), true, purchase, controller);
            context.Validations.Add(validation);

            context.SaveChanges();
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security.Cryptography;

namespace IO.Swagger
{
    public class Hash
    {
        public static string sha256(string text)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(text);
            var hashstring = SHA256.Create();
 
            byte[] hash = hashstring.ComputeHash(bytes);
            string hashString = string.Empty;
            foreach (byte x in hash)
            {
                hashString += String.Format("{0:x2}", x);
            }
            return hashString;
        }

    }
}

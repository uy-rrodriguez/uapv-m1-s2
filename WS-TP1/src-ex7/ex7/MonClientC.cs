class Client {
     static void Main(string[] args) {
         USZip service = new USZip();
         
         System.Xml.XmlElement res = (System.Xml.XmlElement) service.GetInfoByZIP(args[0]);
         
         System.Console.WriteLine("CITY : " + res.FirstChild["CITY"].InnerText);
     }
 }
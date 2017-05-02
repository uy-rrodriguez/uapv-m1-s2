class Client {
     static void Main(string[] args) {
         MonSOAPService service = new MonSOAPService();
         System.Console.WriteLine(service.bonjour("Pepito"));
     }
 }
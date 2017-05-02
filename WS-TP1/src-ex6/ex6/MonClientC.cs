class Client {
     static void Main(string[] args) {
         TextCasing service = new TextCasing();
         System.Console.WriteLine(service.InvertStringCase(args[0]));
     }
 }
package org.cups4j;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintRequestResult;
import java.net.URL;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MyCupsClient {

   public static void main(String []args) throws Exception {
       CupsClient cupsClient = new CupsClient("cups.cups.svc.cluster.local", 6631);
       List<CupsPrinter> printers = cupsClient.getPrinters();

        printers.forEach(cupsPrinter -> {
          System.out.println(cupsPrinter.getName());
          System.out.println(cupsPrinter.getPrinterURL());
          System.out.println(cupsPrinter.getDeviceUri());
            InputStream inputStream = null;
            try {
                inputStream = Files.newInputStream(Paths.get("/usr/app/test.pdf"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintJob printJob = new PrintJob.Builder(inputStream)
                    .jobName("test")
                    .userName("harwey")
                    .copies(2)
                    .build();
            try {
                PrintRequestResult printRequestResult = cupsPrinter.print(printJob);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        boolean flag = true;
        while (flag) {
            Thread.sleep(2000);
        }
   }
}
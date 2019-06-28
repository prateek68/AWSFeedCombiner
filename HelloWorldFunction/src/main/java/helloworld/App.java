package helloworld;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<Object, String>{

    public String handleRequest(final Object input, final Context context) {
        int num= 4;
        List<String> names= new ArrayList<String>();
        List<Integer> sizes= new ArrayList<Integer>();
        InputStream streams= null;
        AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
        try{
            names.add("05eda891-774e-4086-a72a-67b07f4c2db8.xml");
            names.add("6eb1b51e-f7d9-4d00-92c5-9833385fef48.xml");
            names.add("5ced6337-6e51-4d0d-811a-b79de53b3501.xml");
            names.add("8e6f27e2-7116-476c-996c-31ba243f4bce.xml");
            streams= client.getObject("rajat-lamda-edge-test", names.get(0)).getObjectContent();
            sizes.add(streams.available());
            System.out.println("Starts");
            File opFile = new File("output.xml");
            long startTime = System.nanoTime();
            InputStream test = new S3Iterator(names, sizes, streams, client, opFile);
            int a= test.read();
            System.out.println("Ends");
            long endTime   = System.nanoTime();
            System.out.println("Time taken is: " + (endTime-startTime)/1e9);
        }catch(Exception e) {
            System.out.println(e);
        }

        return "Hello from the other side";
    }
}
